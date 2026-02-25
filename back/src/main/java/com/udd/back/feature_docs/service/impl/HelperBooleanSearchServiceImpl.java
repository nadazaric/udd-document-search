package com.udd.back.feature_docs.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.udd.back.core.constants.RegexPattern;
import com.udd.back.core.constants.Maps;
import com.udd.back.feature_docs.enumeration.Operand;
import com.udd.back.feature_docs.enumeration.TokenType;
import com.udd.back.feature_docs.model.Token;
import com.udd.back.feature_docs.service.interf.HelperBooleanSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.regex.Matcher;

@Service
public class HelperBooleanSearchServiceImpl implements HelperBooleanSearchService {

    @Override
    public Query buildQuery(String expression, Set<String> highlightFields) {
        List<Token> tokens = tokenize(expression);
        List<Token> postfix = toPostfix(tokens);

        System.err.println("=== BOOLEAN DEBUG ===");
        System.err.println("EXPR: " + expression);
        System.err.println("TOKENS: " + tokens.stream().map(Token::getRaw).toList());
        System.err.println("RPN: " + postfix.stream().map(Token::getRaw).toList());

        return buildFromPostfix(postfix, highlightFields);
    }

    public Query buildFromPostfix(List<Token> rpnTokens, Set<String> highlightFields) {

        Deque<Query> stack = new ArrayDeque<>();

        for (Token t : rpnTokens) {

            if (t.getType() == TokenType.TERM) {
                String field = t.getField();

                Boolean isKeyword = Maps.FIELDS.get(field);
                if (isKeyword == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field not allowed: " + field);
                }

                Query q = buildTermQuery(field, t.getValue(), t.isPhrase(), isKeyword);

                if (!isKeyword) {
                    highlightFields.add(field);
                }

                stack.push(q);
                continue;
            }

            if (t.getType() == TokenType.OPERAND) {
                Operand op = t.getOperand();

                if (op == Operand.NOT) {
                    if (stack.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT missing operand");
                    }
                    Query a = stack.pop();
                    stack.push(buildNotQuery(a));
                    continue;
                }

                if (stack.size() < 2) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, op.name() + " missing operands");
                }
                Query right = stack.pop();
                Query left = stack.pop();

                if (op == Operand.AND) {
                    stack.push(buildAndQuery(left, right));
                } else if (op == Operand.OR) {
                    stack.push(buildOrQuery(left, right));
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported operand: " + op);
                }

                continue;
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unexpected token in postfix: " + t.getRaw());
        }

        if (stack.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid expression (stack size = " + stack.size() + ")");
        }

        return stack.pop();
    }

    private Query buildTermQuery(String field, String value, boolean phrase, boolean isKeyword) {

        if (isKeyword) {
            return new TermQuery.Builder().field(field).value(value).build()._toQuery();
        }

        if (phrase) {
            return new MatchPhraseQuery.Builder().field(field).query(value).build()._toQuery();
        }

        return new MatchQuery.Builder().field(field).query(value).build()._toQuery();
    }

    private Query buildNotQuery(Query q) {
        return new BoolQuery.Builder().mustNot(q).build()._toQuery();
    }

    private Query buildAndQuery(Query left, Query right) {
        return new BoolQuery.Builder().must(left).must(right).build()._toQuery();
    }

    private Query buildOrQuery(Query left, Query right) {
        return new BoolQuery.Builder().should(left).should(right).minimumShouldMatch("1").build()._toQuery();
    }

    private List<Token> tokenize(String expression) {
        String s = expression.trim();
        List<Token> tokens = new ArrayList<>();

        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);

            if (Character.isWhitespace(c)) { i++; continue; }

            if (c == '(') { tokens.add(Token.leftParen()); i++; continue; }
            if (c == ')') { tokens.add(Token.rightParen()); i++; continue; }

            if (startsWithWord(s, i, "AND")) {
                tokens.add(Token.op(Operand.AND));
                i += 3;
                continue;
            }

            if (startsWithWord(s, i, "OR"))  {
                tokens.add(Token.op(Operand.OR));
                i += 2;
                continue;
            }

            if (startsWithWord(s, i, "NOT")) {
                tokens.add(Token.op(Operand.NOT));
                i += 3;
                continue;
            }

            Matcher m = RegexPattern.TERM_PATTERN.matcher(s.substring(i));
            if (m.find() && m.start() == 0) {
                String field = m.group(1);
                String rawValue = m.group(2);

                boolean phrase = rawValue.startsWith("\"") && rawValue.endsWith("\"");
                String value = phrase ? m.group(3) : rawValue;

                String raw = m.group(0);

                tokens.add(Token.term(raw, field, value, phrase));
                i += raw.length();
                continue;
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token near: " + s.substring(i));
        }

        return tokens;
    }

    private boolean startsWithWord(String s, int i, String word) {
        int end = i + word.length();
        if (end > s.length()) return false;

        if (!s.regionMatches(true, i, word, 0, word.length())) return false;

        if (i > 0) {
            char prev = s.charAt(i - 1);
            if (!Character.isWhitespace(prev) && prev != '(') return false;
        }

        if (end == s.length()) return true;
        char next = s.charAt(end);
        return Character.isWhitespace(next) || next == ')' || next == '(';
    }

    private List<Token> toPostfix(List<Token> tokens) {

        List<Token> output = new ArrayList<>();
        Deque<Token> opStack = new ArrayDeque<>();

        for (Token t : tokens) {

            if (t.getType() == TokenType.TERM) {
                output.add(t);
                continue;
            }

            if (t.getType() == TokenType.OPERAND) {
                while (!opStack.isEmpty() && opStack.peek().getType() == TokenType.OPERAND) {

                    Operand top = opStack.peek().getOperand();
                    Operand cur = t.getOperand();

                    boolean shouldPop;

                    if (cur == Operand.NOT) {
                        shouldPop = top.precedence > cur.precedence;
                    } else {
                        shouldPop = top.precedence >= cur.precedence;
                    }

                    if (!shouldPop) break;
                    output.add(opStack.pop());
                }

                opStack.push(t);
                continue;
            }

            if (t.getType() == TokenType.LEFT_PAREN) {
                opStack.push(t);
                continue;
            }

            if (t.getType() == TokenType.RIGHT_PAREN) {
                while (!opStack.isEmpty() && opStack.peek().getType() != TokenType.LEFT_PAREN) {
                    output.add(opStack.pop());
                }
                if (opStack.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mismatched parentheses");
                }
                opStack.pop();
                continue;
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported token type: " + t.getType());
        }

        while (!opStack.isEmpty()) {
            Token t = opStack.pop();
            if (t.getType() == TokenType.LEFT_PAREN || t.getType() == TokenType.RIGHT_PAREN) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mismatched parentheses");
            }
            output.add(t);
        }

        return output;
    }

}
