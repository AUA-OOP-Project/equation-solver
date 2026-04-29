package com.equationsolver.model;

public class QuadraticEquation extends Equation {

    private final double a;
    private final double b;
    private final double c;

    public QuadraticEquation(String rawInput, double a, double b, double c) {
        super(rawInput, EquationType.QUADRATIC);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b, c};
    }
}