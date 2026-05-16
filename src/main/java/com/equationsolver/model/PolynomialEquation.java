package com.equationsolver.model;

import java.util.Arrays;

/**
 * Base class for all polynomial equations.
 *
 * <p>Stores coefficients ordered from the highest-degree term to the constant:
 * <pre>
 *   degree 1: [a, b]          → ax + b = 0
 *   degree 2: [a, b, c]       → ax² + bx + c = 0
 *   degree 3: [a, b, c, d]    → ax³ + bx² + cx + d = 0
 *   degree 4: [a, b, c, d, e] → ax⁴ + bx³ + cx² + dx + e = 0
 * </pre>
 *
 * <p>{@link #getCoefficients()} always returns a defensive copy.
 * {@link #getDegree()} derives the degree from the coefficient count.
 */
public abstract class PolynomialEquation extends Equation {

    private final double[] coefficients;

    /**
     * @param rawInput     original equation string
     * @param type         resolved {@link EquationType}
     * @param coefficients coefficients from highest-degree to constant term
     */
    protected PolynomialEquation(String rawInput, EquationType type, double... coefficients) {
        super(rawInput, type);
        this.coefficients = coefficients;
    }

    /**
     * Returns a defensive copy of the coefficient array, ordered from the
     * highest-degree term down to the constant.
     */
    @Override
    public double[] getCoefficients() {
        return Arrays.copyOf(coefficients, coefficients.length);
    }

    /**
     * Returns the degree of the polynomial (number of coefficients minus one).
     *
     * @return polynomial degree ≥ 1
     */
    public int getDegree() {
        return coefficients.length - 1;
    }
}