package com.equationsolver.model;

/**
 * Model for a tangent equation of the form {@code tan(x) = rhs}.
 * Period is {@code π}; {@code rhs} is unrestricted (all real numbers are valid).
 */
public class TangentEquation extends TrigonometricEquation {

    public TangentEquation(String rawInput, double rhs) {
        super(rawInput, EquationType.TANGENT, rhs, Math.PI, new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY});
    }

    @Override
    public String getFunctionName() {
        return "tan";
    }
}