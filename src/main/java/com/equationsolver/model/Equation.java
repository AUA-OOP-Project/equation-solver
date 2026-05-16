package com.equationsolver.model;

/**
 * Abstract base class for all equation types in the solver.
 *
 * <p>Every equation holds the original user-supplied string ({@code rawInput})
 * and a resolved {@link EquationType} so that parsers and solvers can route
 * processing without {@code instanceof} checks.
 *
 * <p>Concrete subclasses are responsible for providing their coefficients via
 * {@link #getCoefficients()}, whose length and semantics depend on the equation
 * type (e.g., {@code [a, b]} for linear, {@code [a, b, c]} for quadratic).
 */
public abstract class Equation {

    private final String rawInput;
    private final EquationType type;

    /**
     * @param rawInput the original equation string as entered by the user
     * @param type     the resolved {@link EquationType} for this equation
     */
    protected Equation(String rawInput, EquationType type) {
        this.rawInput = rawInput;
        this.type = type;
    }

    /**
     * Returns the original equation string as entered by the user.
     *
     * @return raw input string, never {@code null}
     */
    public String getRawInput() {
        return rawInput;
    }

    /**
     * Returns the type of this equation.
     *
     * @return {@link EquationType}, never {@code null}
     */
    public EquationType getType() {
        return type;
    }

    /**
     * Returns the coefficients that fully describe this equation.
     *
     * <p>The order and meaning of the returned values depend on the concrete
     * subclass. For polynomial equations the coefficients are ordered from the
     * highest-degree term to the constant term.
     *
     * @return a defensive copy of the internal coefficient array
     */
    public abstract double[] getCoefficients();
}