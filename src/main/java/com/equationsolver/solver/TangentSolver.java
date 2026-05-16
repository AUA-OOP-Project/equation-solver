package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;
import com.equationsolver.model.TrigonometricEquation;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves tangent equations of the form {@code tan(x) = rhs}.
 *
 * <p>Returns the principal value {@code x = arctan(rhs)} in (-π/2, π/2).
 * Unlike sine and cosine, tangent has no domain restriction on {@code rhs},
 * so a solution always exists for any real {@code rhs}.
 * The general solution is {@code x = arctan(rhs) + πk} for any integer k.
 */
public class TangentSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.TANGENT) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        TrigonometricEquation eq = (TrigonometricEquation) equation;
        double rhs = eq.getRhs();

        List<String> steps = new ArrayList<>();
        steps.add("Equation: tan(x) = " + format(rhs));

        double x1 = Math.atan(rhs);
        double x2 = x1 + Math.PI;

        steps.add("Principal value: x₁ = arctan(" + format(rhs) + ") = " + format(x1) + " rad");
        steps.add("Second value in [0, 2π]: x₂ = x₁ + π = " + format(x2) + " rad");
        steps.add("General solution: x = x₁ + πk, k ∈ ℤ");

        return Solution.of(new double[]{x1, x2}, EquationType.TANGENT, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.format("%.4f", v);
    }
}