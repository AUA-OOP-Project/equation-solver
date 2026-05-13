package com.equationsolver.model;

// Represents: sin(x) = rhs
public class SineEquation extends TrigonometricEquation {

    public SineEquation(String rawInput, double rhs) {
        super(rawInput, EquationType.SINE, rhs, 2 * Math.PI, new double[]{-1, 1});
    }

    @Override
    public String getFunctionName() {
        return "sin";
    }
}
