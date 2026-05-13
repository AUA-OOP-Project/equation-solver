package com.equationsolver.model;

// Represents: ax⁴ + bx³ + cx² + dx + e = 0
public class QuarticEquation extends PolynomialEquation {

    public QuarticEquation(String rawInput, double a, double b, double c, double d, double e) {
        super(rawInput, EquationType.QUARTIC, a, b, c, d, e);
    }
}