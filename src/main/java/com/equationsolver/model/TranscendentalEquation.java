package com.equationsolver.model;

/**
 * Base class for transcendental equations — equations that cannot be expressed
 * as finite polynomials (e.g., exponential, logarithmic, trigonometric).
 *
 * <p>All concrete subclasses share a single {@code rhs} (right-hand side) value
 * representing the numeric constant the transcendental expression is equated to.
 * For example, {@code sin(x) = 0.5} has {@code rhs = 0.5}.
 *
 * <p>{@link #getCoefficients()} returns an empty array because transcendental
 * equations are not described by polynomial coefficients.
 */
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