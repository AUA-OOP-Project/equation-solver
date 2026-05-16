package com.equationsolver.model;

/**
 * Model for a rational equation of the form {@code P(x) / Q(x) = 0}.
 *
 * <p>Stores the numerator and denominator as separate polynomial coefficient arrays,
 * ordered from the highest-degree term to the constant.
 * {@link #getCoefficients()} returns the numerator coefficients.
 */
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