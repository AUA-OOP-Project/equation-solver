package com.equationsolver.model;

// Represents: log_base(x) = rhs  (e.g. log₂(x) = 3)
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