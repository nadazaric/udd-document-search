package com.udd.back.feature_docs.enumeration;

public enum Operand {
    NOT(3, true),
    AND(2, false),
    OR(1, false);

    public final int precedence;
    public final boolean unary;

    Operand(int precedence, boolean unary) {
        this.precedence = precedence;
        this.unary = unary;
    }

}
