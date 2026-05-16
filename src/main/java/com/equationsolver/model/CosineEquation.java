package com.equationsolver.model;

/**
 * Model for a cosine equation of the form {@code cos(x) = rhs}.
 * Period is {@code 2π}; valid domain for {@code rhs} is {@code [-1, 1]}.
 */
public class CosineEquation extends TrigonometricEquation {

    public CosineEquation(String rawInput, double rhs) {
        super(rawInput, EquationType.COSINE, rhs, 2 * Math.PI, new double[]{-1, 1});
    }

    @Override
    public String getFunctionName() {
        return "cos";
    }
}
