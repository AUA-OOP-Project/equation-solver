package com.equationsolver.solver;

import com.equationsolver.model.Equation;
import com.equationsolver.model.Solution;

/**
 * Abstract base class for all equation solvers, implementing the
 * <em>Template Method</em> pattern.
 *
 * <p>The public entry point {@link #solve(Equation)} is {@code final} and
 * enforces a two-step pipeline:
 * <ol>
 *   <li>{@link #validate(Equation)} – verifies that the equation's type matches
 *       this solver; throws {@link com.equationsolver.exception.InvalidEquationException}
 *       on mismatch.</li>
 *   <li>{@link #doSolve(Equation)} – performs the actual mathematical computation
 *       and returns a {@link Solution}.</li>
 * </ol>
 *
 * <p>Concrete subclasses must implement both abstract methods.
 * They should never call each other's {@code solve()} directly; instead, internal
 * delegation (e.g., {@link QuarticSolver} reusing {@link CubicSolver}) is done
 * by instantiating the helper solver and calling {@code solve()} on it.
 */
public abstract class EquationSolver implements Solvable {

    /**
     * Validates and solves the given equation.
     *
     * @param equation the equation to solve; must match this solver's type
     * @return a {@link Solution} describing the result
     * @throws com.equationsolver.exception.InvalidEquationException if the
     *         equation type does not match this solver
     */
    @Override
    public final Solution solve(Equation equation) {
        validate(equation);
        return doSolve(equation);
    }

    /**
     * Checks that {@code equation.getType()} matches the type this solver handles.
     *
     * @param equation the equation to validate
     * @throws com.equationsolver.exception.InvalidEquationException on type mismatch
     */
    protected abstract void validate(Equation equation);

    /**
     * Performs the mathematical solve and returns the result.
     * Called only after {@link #validate(Equation)} succeeds.
     *
     * @param equation a validated equation of the correct type
     * @return a {@link Solution} with roots (or no-solution / infinite-solutions)
     */
    protected abstract Solution doSolve(Equation equation);
}