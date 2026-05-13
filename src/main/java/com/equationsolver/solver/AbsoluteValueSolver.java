package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.*;

import java.util.ArrayList;
import java.util.List;

public class AbsoluteValueSolver extends EquationSolver {

    private final LinearSolver linearSolver = new LinearSolver();

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.ABSOLUTE_VALUE) {
            throw new InvalidEquationException(equation.getRawInput());
        }
        AbsoluteValueEquation abs = (AbsoluteValueEquation) equation;
        if (abs.getA() == 0) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        AbsoluteValueEquation abs = (AbsoluteValueEquation) equation;

        double a = abs.getA();
        double b = abs.getB();
        double c = abs.getC();
        double d = abs.getD();

        List<String> steps = new ArrayList<>();
        steps.add("Original: |" + fmt(a) + "x + " + fmt(b) + "| + " + fmt(c) + " = " + fmt(d));

        double k = d - c;
        steps.add("Normalize: move constant to rhs → |" + fmt(a) + "x + " + fmt(b) + "| = " + fmt(k));


        if (k < 0) {
            steps.add("k = " + fmt(k) + " < 0 → no solution (absolute value cannot be negative)");
            return Solution.noSolution(EquationType.ABSOLUTE_VALUE, steps);
        }

        steps.add("Split into two cases:");
        steps.add("  Case 1: " + fmt(a) + "x + " + fmt(b) + " = "  + fmt(k));
        steps.add("  Case 2: " + fmt(a) + "x + " + fmt(b) + " = -" + fmt(k));


        double x1 = solveLinear(a, b - k, steps, 1);
        double x2 = solveLinear(a, b + k, steps, 2);


        List<Double> validRoots = new ArrayList<>();

        if (isValid(x1, a, b, k)) {
            steps.add("  x₁ = " + fmt(x1) + " → valid (rhs ≥ 0)");
            validRoots.add(x1);
        } else {
            steps.add("  x₁ = " + fmt(x1) + " → rejected (makes rhs negative)");
        }


        if (k != 0) {
            if (isValid(x2, a, b, k)) {
                steps.add("  x₂ = " + fmt(x2) + " → valid (rhs ≥ 0)");
                validRoots.add(x2);
            } else {
                steps.add("  x₂ = " + fmt(x2) + " → rejected (makes rhs negative)");
            }
        }

        if (validRoots.isEmpty()) {
            return Solution.noSolution(EquationType.ABSOLUTE_VALUE, steps);
        }

        double[] roots = validRoots.stream().mapToDouble(Double::doubleValue).toArray();
        return Solution.of(roots, EquationType.ABSOLUTE_VALUE, steps);
    }

    private double solveLinear(double a, double b, List<String> steps, int caseNum) {
        LinearEquation eq = new LinearEquation("case" + caseNum, a, b);
        Solution solution  = linearSolver.solve(eq);
        double x           = solution.getRoots()[0];
        steps.add("  Case " + caseNum + " solved: x = " + fmt(x));
        return x;
    }


    private boolean isValid(double x, double a, double b, double k) {
        double lhs = Math.abs(a * x + b);
        return Math.abs(lhs - k) < 1e-9;
    }


    private String fmt(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}