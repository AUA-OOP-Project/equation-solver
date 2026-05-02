package com.equationsolver.model;

// Represents: base^x = rhs  (e.g. 2^x = 8)
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