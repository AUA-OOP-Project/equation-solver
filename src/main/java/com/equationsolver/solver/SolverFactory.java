package com.equationsolver.solver;

import com.equationsolver.exception.UnsupportedEquationTypeException;
import com.equationsolver.model.EquationType;

/**
 * Factory that instantiates the correct {@link EquationSolver} for a given
 * {@link EquationType}.
 *
 * <p>Usage:
 * <pre>{@code
 *   EquationType type    = ParserFactory.detectType(input);
 *   Equation     eq      = ParserFactory.getParser(type).parse(input);
 *   Solution     result  = SolverFactory.getSolver(type).solve(eq);
 * }</pre>
 *
 * <p>Throws {@link com.equationsolver.exception.UnsupportedEquationTypeException}
 * for any type that has no registered solver (currently {@link EquationType#HIGHER_DEGREE}).
 */
public class SolverFactory {

    /**
     * Returns a new {@link EquationSolver} instance for the given type.
     *
     * @param type the {@link EquationType} to look up
     * @return a fresh solver that handles {@code type}
     * @throws com.equationsolver.exception.UnsupportedEquationTypeException
     *         if no solver is registered for {@code type}
     */
    public static EquationSolver getSolver(EquationType type) {
        return switch (type) {
            case LINEAR      -> new LinearSolver();
            case QUADRATIC   -> new QuadraticSolver();
            case CUBIC       -> new CubicSolver();
            case QUARTIC     -> new QuarticSolver();
            case EXPONENTIAL    -> new ExponentialSolver();
            case LOGARITHMIC    -> new LogarithmicSolver();
            case RATIONAL       -> new RationalSolver();
            case ABSOLUTE_VALUE -> new AbsoluteValueSolver();
            case SINE           -> new SineSolver();
            case COSINE         -> new CosineSolver();
            case TANGENT        -> new TangentSolver();
            default             -> throw new UnsupportedEquationTypeException(type);
        };
    }
}
