package com.equationsolver.model;

// Represents: ax + b = 0
public class LinearEquation extends Equation {

    private final double a;
    private final double b;

    public LinearEquation(String rawInput, double a, double b) {
        super(rawInput, EquationType.LINEAR);
        this.a = a;
        this.b = b;
    }

    // Returns [a, b]
    @Override
    public double[] getCoefficients() {
        return new double[]{a, b};
    }
}