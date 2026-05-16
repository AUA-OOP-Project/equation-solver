package com.equationsolver.model;

/**
 * Model for a linear equation of the form {@code ax + b = 0}.
 * Coefficients are stored as {@code [a, b]}.
 */
public class LinearEquation extends PolynomialEquation {

    public LinearEquation(String rawInput, double a, double b) {
        super(rawInput, EquationType.LINEAR, a, b);
    }
}