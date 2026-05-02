package com.equationsolver.model;

public class RationalEquation extends Equation {

    private final double[] numerator;
    private final double[] denominator;

    public RationalEquation(String rawInput, double[] numerator, double[] denominator) {
        super(rawInput, EquationType.RATIONAL);
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double[] getCoefficients() {
        return numerator;
    }

    public double[] getNumerator() {
        return numerator;
    }

    public double[] getDenominator() {
        return denominator;
    }
}