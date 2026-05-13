package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;
import com.equationsolver.model.TrigonometricEquation;

import java.util.ArrayList;
import java.util.List;

// Solves sin(x) = rhs, returns principal values in [-π/2, π/2]
public class SineSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.SINE) {
            throw new InvalidEquationException(equation.getRawInput());
        }
        TrigonometricEquation eq = (TrigonometricEquation) equation;
        double rhs = eq.getRhs();
        if (rhs < -1 || rhs > 1) {
            throw new InvalidEquationException("sin(x) = " + rhs + " has no solution: rhs must be in [-1, 1]");
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        TrigonometricEquation eq = (TrigonometricEquation) equation;
        double rhs = eq.getRhs();

        List<String> steps = new ArrayList<>();
        steps.add("Equation: sin(x) = " + format(rhs));

        double x1 = Math.asin(rhs);
        double x2 = Math.PI - x1;

        steps.add("Principal value: x₁ = arcsin(" + format(rhs) + ") = " + format(x1) + " rad");
        steps.add("Second value in [0, 2π]: x₂ = π - x₁ = " + format(x2) + " rad");
        steps.add("General solution: x = x₁ + 2πk  or  x = x₂ + 2πk, k ∈ ℤ");

        return Solution.of(new double[]{x1, x2}, EquationType.SINE, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.format("%.4f", v);
    }
}
