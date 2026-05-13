package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class QuadraticSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.QUADRATIC) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        double[] c = equation.getCoefficients();
        double a = c[0], b = c[1], cc = c[2];

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: ax² + bx + c = 0,  a=" + format(a) + ", b=" + format(b) + ", c=" + format(cc));
        steps.add("Discriminant Δ = b² - 4ac");
        double discriminant = b * b - 4 * a * cc;
        steps.add("Δ = " + format(b) + "² - 4×" + format(a) + "×" + format(cc) + " = " + format(discriminant));

        if (discriminant < 0) {
            steps.add("Δ < 0  →  no real roots");
            return Solution.noSolution(EquationType.QUADRATIC, steps);
        }

        if (discriminant == 0) {
            double root = -b / (2 * a);
            steps.add("Δ = 0  →  one real root");
            steps.add("x = -b / 2a = " + format(-b) + " / " + format(2 * a) + " = " + format(root));
            return Solution.of(new double[]{root}, EquationType.QUADRATIC, steps);
        }

        double sqrtDisc = Math.sqrt(discriminant);
        steps.add("Δ > 0  →  two real roots");
        steps.add("x = (-b ± √Δ) / 2a");
        double root1 = (-b + sqrtDisc) / (2 * a);
        double root2 = (-b - sqrtDisc) / (2 * a);
        steps.add("x₁ = (" + format(-b) + " + √" + format(discriminant) + ") / " + format(2 * a) + " = " + format(root1));
        steps.add("x₂ = (" + format(-b) + " - √" + format(discriminant) + ") / " + format(2 * a) + " = " + format(root2));

        return Solution.of(new double[]{root1, root2}, EquationType.QUADRATIC, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}