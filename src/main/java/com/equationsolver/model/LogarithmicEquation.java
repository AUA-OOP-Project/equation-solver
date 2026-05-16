package com.equationsolver.model;

/**
 * Model for a logarithmic equation of the form {@code log_base(x) = rhs}
 * (e.g. {@code log₂(x) = 3} or {@code ln(x) = 1}).
 *
 * <p>The natural logarithm {@code ln} is represented with {@code base = Math.E}.
 * The common logarithm {@code log} (no base specified) uses {@code base = 10}.
 */
public class LogarithmicEquation extends TranscendentalEquation {

    private final double base;

    public LogarithmicEquation(String rawInput, double base, double rhs) {
        super(rawInput, EquationType.LOGARITHMIC, rhs);
        this.base = base;
    }

    public double getBase() {
        return base;
    }
}