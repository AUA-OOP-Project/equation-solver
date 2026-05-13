package com.equationsolver.model;

// Represents: ax + b = 0
public class LinearEquation extends PolynomialEquation {

    public LinearEquation(String rawInput, double a, double b) {
        super(rawInput, EquationType.LINEAR, a, b);
    }
}