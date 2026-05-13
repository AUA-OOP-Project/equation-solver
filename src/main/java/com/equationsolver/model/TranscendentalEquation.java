package com.equationsolver.model;

public abstract class TranscendentalEquation extends Equation {

    private final double rhs;

    protected TranscendentalEquation(String rawInput, EquationType type, double rhs) {
        super(rawInput, type);
        this.rhs = rhs;
    }

    public double getRhs() {
        return rhs;
    }

    // transcendental equations don't have polynomial coefficients
    @Override
    public double[] getCoefficients() {
        return new double[0];
    }
}