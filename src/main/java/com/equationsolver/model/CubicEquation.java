package com.equationsolver.model;

// Represents: ax³ + bx² + cx + d = 0
public class CubicEquation extends PolynomialEquation {

    public CubicEquation(String rawInput, double a, double b, double c, double d) {
        super(rawInput, EquationType.CUBIC, a, b, c, d);
    }
}