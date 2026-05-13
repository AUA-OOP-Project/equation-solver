package com.equationsolver.model;


public class AbsoluteValueEquation extends Equation {

    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public AbsoluteValueEquation(String rawInput, double a, double b, double c, double d) {
        super(rawInput, EquationType.ABSOLUTE_VALUE);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public double getD() { return d; }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b, c, d};
    }
}