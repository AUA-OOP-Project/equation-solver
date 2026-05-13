package com.equationsolver.model;

import java.util.Arrays;

public abstract class PolynomialEquation extends Equation {

    private final double[] coefficients;

    protected PolynomialEquation(String rawInput, EquationType type, double... coefficients) {
        super(rawInput, type);
        this.coefficients = coefficients;
    }

    @Override
    public double[] getCoefficients() {
        return Arrays.copyOf(coefficients, coefficients.length);
    }

    public int getDegree() {
        return coefficients.length - 1;
    }
}