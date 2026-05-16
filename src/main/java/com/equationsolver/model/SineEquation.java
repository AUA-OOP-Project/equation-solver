package com.equationsolver.model;

/**
 * Model for a sine equation of the form {@code sin(x) = rhs}.
 * Period is {@code 2π}; valid domain for {@code rhs} is {@code [-1, 1]}.
 */
public class SineEquation extends TrigonometricEquation {

    public SineEquation(String rawInput, double rhs) {
        super(rawInput, EquationType.SINE, rhs, 2 * Math.PI, new double[]{-1, 1});
    }

    @Override
    public String getFunctionName() {
        return "sin";
    }
}
