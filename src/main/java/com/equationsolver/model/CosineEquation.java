package com.equationsolver.model;

// Represents: cos(x) = rhs
public class CosineEquation extends TrigonometricEquation {

    public CosineEquation(String rawInput, double rhs) {
        super(rawInput, EquationType.COSINE, rhs, 2 * Math.PI, new double[]{-1, 1});
    }

    @Override
    public String getFunctionName() {
        return "cos";
    }
}
