package com.equationsolver.model;

public class TangentEquation extends TrigonometricEquation {

    public TangentEquation(String rawInput, double rhs) {
        super(rawInput, EquationType.TANGENT, rhs, Math.PI, new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY});
    }

    @Override
    public String getFunctionName() {
        return "tan";
    }
}