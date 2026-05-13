package com.equationsolver.model;

public abstract class TrigonometricEquation extends TranscendentalEquation {

    private final double period;
    private final double[] domain;

    protected TrigonometricEquation(String rawInput, EquationType type, double rhs, double period, double[] domain) {
        super(rawInput, type, rhs);
        this.period = period;
        this.domain = domain;
    }

    public double getPeriod() {
        return period;
    }

    public double[] getDomain() {
        return domain;
    }

    public abstract String getFunctionName();
}