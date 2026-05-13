package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;
import com.equationsolver.model.TrigonometricEquation;

import java.util.ArrayList;
import java.util.List;

// Solves cos(x) = rhs, returns principal values in [0, π]
public class CosineSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.COSINE) {
            throw new InvalidEquationException(equation.getRawInput());
        }
        TrigonometricEquation eq = (TrigonometricEquation) equation;
        double rhs = eq.getRhs();
        if (rhs < -1 || rhs > 1) {
            throw new InvalidEquationException("cos(x) = " + rhs + " has no solution: rhs must be in [-1, 1]");
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        TrigonometricEquation eq = (TrigonometricEquation) equation;
        double rhs = eq.getRhs();

        List<String> steps = new ArrayList<>();
        steps.add("Equation: cos(x) = " + format(rhs));

        double x1 = Math.acos(rhs);
        double x2 = -x1;

        steps.add("Principal value: x₁ = arccos(" + format(rhs) + ") = " + format(x1) + " rad");
        steps.add("Second value: x₂ = -x₁ = " + format(x2) + " rad");
        steps.add("General solution: x = ±x₁ + 2πk, k ∈ ℤ");

        return Solution.of(new double[]{x1, x2}, EquationType.COSINE, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.format("%.4f", v);
    }
}
