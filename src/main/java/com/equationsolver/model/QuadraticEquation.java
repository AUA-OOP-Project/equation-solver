package com.equationsolver.model;

// Represents: ax² + bx + c = 0
public class QuadraticEquation extends PolynomialEquation {

    public QuadraticEquation(String rawInput, double a, double b, double c) {
        super(rawInput, EquationType.QUADRATIC, a, b, c);
    }
}