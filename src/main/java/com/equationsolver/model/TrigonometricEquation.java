package com.equationsolver.model;

/**
 * Base class for trigonometric equations of the form {@code f(x) = rhs},
 * where {@code f} is one of sin, cos, or tan.
 *
 * <p>Each concrete subclass provides:
 * <ul>
 *   <li>{@code period} – length of one full cycle (2π for sin/cos, π for tan).</li>
 *   <li>{@code domain} – valid range for {@code rhs}: {@code [-1, 1]} for sin/cos,
 *       unrestricted {@code (-∞, +∞)} for tan.</li>
 *   <li>{@link #getFunctionName()} – the name of the trig function (e.g., "sin").</li>
 * </ul>
 */
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