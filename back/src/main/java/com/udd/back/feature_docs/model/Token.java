package com.udd.back.feature_docs.model;

import com.udd.back.feature_docs.enumeration.Operand;
import com.udd.back.feature_docs.enumeration.TokenType;

public class Token {

    private TokenType type;
    private String raw;
    private String field;
    private String value;
    private boolean phrase;
    private Operand operand;

    private Token(TokenType type, String raw, String field, String value, boolean phrase, Operand operand) {
        this.type = type;
        this.raw = raw;
        this.field = field;
        this.value = value;
        this.phrase = phrase;
        this.operand = operand;
    }

    public static Token leftParen() {
        return new Token(TokenType.LEFT_PAREN, "(", null, null, false, null);
    }

    public static Token rightParen() {
        return new Token(TokenType.RIGHT_PAREN, ")", null, null, false, null);
    }

    public static Token op(Operand operand) {
        return new Token(TokenType.OPERAND, operand.name(), null, null, false, operand);
    }

    public static Token term(String raw, String field, String value, boolean phrase) {
        return new Token(TokenType.TERM, raw, field, value, phrase, null);
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isPhrase() {
        return phrase;
    }

    public void setPhrase(boolean phrase) {
        this.phrase = phrase;
    }

    public Operand getOperand() {
        return operand;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
    }
}
