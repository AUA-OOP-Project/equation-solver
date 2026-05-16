package com.equationsolver.exception;

import com.equationsolver.model.EquationType;

/**
 * Thrown by {@link com.equationsolver.parser.ParserFactory} and
 * {@link com.equationsolver.solver.SolverFactory} when no parser or solver is
 * registered for the requested {@link EquationType}.
 *
 * <p>This is an unchecked exception indicating a programming error or an attempt
 * to handle an equation type that is not yet implemented.
 */
public class UnsupportedEquationTypeException extends RuntimeException {

    /**
     * @param type the {@link EquationType} for which no handler is registered
     */
    public UnsupportedEquationTypeException(EquationType type) {
        super("No solver or parser registered for equation type: " + type);
    }
}