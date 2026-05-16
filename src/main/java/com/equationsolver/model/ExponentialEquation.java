package com.equationsolver.model;

/**
 * Model for an exponential equation of the form {@code base^x = rhs} (e.g. {@code 2^x = 8}).
 *
 * <p>Stores the {@code base} separately from the {@code rhs}, both of which
 * are required by {@link com.equationsolver.solver.ExponentialSolver}.
 */
public class ExponentialEquation extends TranscendentalEquation {

    private final double base;

    public ExponentialEquation(String rawInput, double base, double rhs) {
        super(rawInput, EquationType.EXPONENTIAL, rhs);
        this.base = base;
    }

    public double getBase() {
        return base;
    }
}