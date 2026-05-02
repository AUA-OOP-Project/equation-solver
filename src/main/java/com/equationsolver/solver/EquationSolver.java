package com.equationsolver.solver;

import com.equationsolver.model.Equation;
import com.equationsolver.model.Solution;

public abstract class EquationSolver implements Solvable {

    @Override
    public final Solution solve(Equation equation) {
        validate(equation);
        return doSolve(equation);
    }

    protected abstract void validate(Equation equation);

    protected abstract Solution doSolve(Equation equation);
}