package com.equationsolver.exception;

/**
 * Thrown when an equation string cannot be parsed or when a solver receives
 * an equation whose type does not match the solver's expected type.
 *
 * <p>This is an unchecked exception. Callers (e.g., the GUI and CLI layers)
 * should catch it to display a user-friendly error message.
 */
public class InvalidEquationException extends RuntimeException {

    /**
     * @param input the raw equation string that failed to parse
     */
    public InvalidEquationException(String input) {
        super("Cannot parse equation: \"" + input + "\"");
    }

    /**
     * @param input the raw equation string that failed to parse
     * @param cause the underlying cause of the failure
     */
    public InvalidEquationException(String input, Throwable cause) {
        super("Cannot parse equation: \"" + input + "\"", cause);
    }
}