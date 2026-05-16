package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves rational equations of the form {@code P(x) / Q(x) = 0}.
 *
 * <p>A rational equation equals zero when its numerator equals zero and the
 * denominator is non-zero. The solver:
 * <ol>
 *   <li>Solves the numerator polynomial {@code P(x) = 0} using the appropriate
 *       delegate solver (linear, quadratic, or cubic).</li>
 *   <li>Filters out any root {@code x} for which {@code Q(x) = 0}
 *       (these are excluded values / vertical asymptotes).</li>
 * </ol>
 */
public class RationalSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.RATIONAL) {
            throw new InvalidEquationException(equation.getRawInput());
        }
        RationalEquation re = (RationalEquation) equation;
        if (re.getDenominator() == null || re.getDenominator().length == 0) {
            throw new InvalidEquationException("Denominator cannot be empty.");
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        RationalEquation re = (RationalEquation) equation;

        double[] num = re.getNumerator();
        double[] den = re.getDenominator();

        List<String> steps = new ArrayList<>();
        steps.add("Rational equation: set numerator = 0, exclude denominator zeros");

        double[] candidateRoots = solvePolynomial(re.getRawInput(), num);

        List<Double> validRoots = new ArrayList<>();
        for (double root : candidateRoots) {
            double denVal = evaluate(den, root);
            if (Math.abs(denVal) > 1e-9) {
                validRoots.add(root);
            } else {
                steps.add("x = " + format(root) + " excluded: makes denominator = 0");
            }
        }

        if (validRoots.isEmpty()) {
            steps.add("No valid roots remain after exclusion");
            return Solution.noSolution(EquationType.RATIONAL, steps);
        }

        double[] roots = validRoots.stream().mapToDouble(Double::doubleValue).toArray();

        StringBuilder sb = new StringBuilder("Roots: ");
        for (int i = 0; i < roots.length; i++) {
            sb.append("x").append(i + 1).append(" = ").append(format(roots[i]));
            if (i < roots.length - 1) sb.append(", ");
        }
        steps.add(sb.toString());

        return Solution.of(roots, EquationType.RATIONAL, steps);
    }

    private double[] solvePolynomial(String raw, double[] coeffs) {
        int degree = coeffs.length - 1;
        Equation eq = switch (degree) {
            case 1 -> new LinearEquation(raw, coeffs[0], coeffs[1]);
            case 2 -> new QuadraticEquation(raw, coeffs[0], coeffs[1], coeffs[2]);
            case 3 -> new CubicEquation(raw, coeffs[0], coeffs[1], coeffs[2], coeffs[3]);
            case 4 -> new QuarticEquation(raw, coeffs[0], coeffs[1], coeffs[2], coeffs[3], coeffs[4]);
            default -> throw new InvalidEquationException("Unsupported numerator degree: " + degree);
        };

        EquationSolver solver = switch (degree) {
            case 1 -> new LinearSolver();
            case 2 -> new QuadraticSolver();
            case 3 -> new CubicSolver();
            case 4 -> new QuarticSolver();
            default -> throw new InvalidEquationException("Unsupported numerator degree: " + degree);
        };

        Solution s = solver.solve(eq);
        return s.hasSolution() ? s.getRoots() : new double[0];
    }

    private double evaluate(double[] coeffs, double x) {
        double result = 0;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * Math.pow(x, coeffs.length - 1 - i);
        }
        return result;
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}