package com.equationsolver.solver;

import com.equationsolver.model.Equation;
import com.equationsolver.model.Solution;

/**
 * Contract for all equation solvers.
 *
 * <p>Implemented by {@link EquationSolver}, which enforces the validate → solve
 * pipeline via the Template Method pattern. Direct implementations outside of
 * {@link EquationSolver} are not expected.
 */
public interface Solvable {

    /**
     * Solves the given equation and returns the result.
     *
     * @param equation the equation to solve
     * @return a {@link Solution} describing the roots or outcome
     * @throws com.equationsolver.exception.InvalidEquationException if the
     *         equation type does not match this solver
     */
    Solution solve(Equation equation);
}