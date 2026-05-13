package com.equationsolver.solver;

import com.equationsolver.exception.UnsupportedEquationTypeException;
import com.equationsolver.model.EquationType;

public class SolverFactory {

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
