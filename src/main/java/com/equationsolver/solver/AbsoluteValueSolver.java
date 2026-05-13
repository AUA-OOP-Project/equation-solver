package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.AbsoluteValueEquation;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

// Solves |ax + b| = c
public class AbsoluteValueSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.ABSOLUTE_VALUE) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        AbsoluteValueEquation eq = (AbsoluteValueEquation) equation;
        double a = eq.getA();
        double b = eq.getB();
        double c = eq.getC();

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: |ax + b| = c,  a=" + format(a) + ", b=" + format(b) + ", c=" + format(c));

        if (c < 0) {
            steps.add("c < 0  →  absolute value is always ≥ 0, no real solution");
            return Solution.noSolution(EquationType.ABSOLUTE_VALUE, steps);
        }

        if (c == 0) {
            steps.add("c = 0  →  ax + b = 0  →  x = " + format(-b / a));
            double x = -b / a;
            return Solution.of(new double[]{x}, EquationType.ABSOLUTE_VALUE, steps);
        }

        // |ax + b| = c  →  ax + b = c  OR  ax + b = -c
        steps.add("Case 1: ax + b = c  →  ax = c - b  →  x = (c - b) / a");
        double x1 = (c - b) / a;
        steps.add("x₁ = (" + format(c) + " - " + format(b) + ") / " + format(a) + " = " + format(x1));

        steps.add("Case 2: ax + b = -c  →  ax = -c - b  →  x = (-c - b) / a");
        double x2 = (-c - b) / a;
        steps.add("x₂ = (-" + format(c) + " - " + format(b) + ") / " + format(a) + " = " + format(x2));

        return Solution.of(new double[]{x1, x2}, EquationType.ABSOLUTE_VALUE, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}
