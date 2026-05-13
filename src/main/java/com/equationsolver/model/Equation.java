package com.equationsolver.model;

public abstract class Equation {

    private final String rawInput;
    private final EquationType type;

    protected Equation(String rawInput, EquationType type) {
        this.rawInput = rawInput;
        this.type = type;
    }

    public String getRawInput() {
        return rawInput;
    }

    public EquationType getType() {
        return type;
    }

    public abstract double[] getCoefficients();
}