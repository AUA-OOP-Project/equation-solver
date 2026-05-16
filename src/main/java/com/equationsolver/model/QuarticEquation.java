package com.equationsolver.model;

/**
 * Model for a quartic equation of the form {@code ax⁴ + bx³ + cx² + dx + e = 0}.
 * Coefficients are stored as {@code [a, b, c, d, e]}.
 */
public class QuarticEquation extends PolynomialEquation {

    public QuarticEquation(String rawInput, double a, double b, double c, double d, double e) {
        super(rawInput, EquationType.QUARTIC, a, b, c, d, e);
    }
}