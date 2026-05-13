package com.equationsolver.model;

// Represents: |ax + b| = c
public class AbsoluteValueEquation extends Equation {

    private final double a;
    private final double b;
    private final double c;

    public AbsoluteValueEquation(String rawInput, double a, double b, double c) {
        super(rawInput, EquationType.ABSOLUTE_VALUE);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b, c};
    }
}
