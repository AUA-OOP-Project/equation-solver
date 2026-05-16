package com.equationsolver.model;

/**
 * Model for a quadratic equation of the form {@code ax² + bx + c = 0}.
 * Coefficients are stored as {@code [a, b, c]}.
 */
public class QuadraticEquation extends PolynomialEquation {

    public QuadraticEquation(String rawInput, double a, double b, double c) {
        super(rawInput, EquationType.QUADRATIC, a, b, c);
    }
}