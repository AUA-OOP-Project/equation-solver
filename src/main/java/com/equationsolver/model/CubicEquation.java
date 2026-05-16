package com.equationsolver.model;

/**
 * Model for a cubic equation of the form {@code ax³ + bx² + cx + d = 0}.
 * Coefficients are stored as {@code [a, b, c, d]}.
 */
public class CubicEquation extends PolynomialEquation {

    public CubicEquation(String rawInput, double a, double b, double c, double d) {
        super(rawInput, EquationType.CUBIC, a, b, c, d);
    }
}