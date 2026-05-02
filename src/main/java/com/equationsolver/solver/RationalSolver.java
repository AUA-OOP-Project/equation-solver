package com.equationsolver.solver;

import com.equationsolver.model.*;

import java.util.ArrayList;
import java.util.List;

public class RationalSolver extends EquationSolver {

    private final EquationParser parser = new EquationParser();

    @Override
    protected void validate(Equation equation) {
        if (!(equation instanceof RationalEquation)) {
            throw new IllegalArgumentException("Equation must be a RationalEquation.");
        }
        RationalEquation re = (RationalEquation) equation;
        if (re.getDenominator() == null || re.getDenominator().length == 0) {
            throw new IllegalArgumentException("Denominator cannot be empty.");
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        RationalEquation re = (RationalEquation) equation;

        double[] num = re.getNumerator();
        double[] den = re.getDenominator();

        // solve numerator = 0
        double[] candidateRoots = solvePolynomial(re.getRawInput(), num);

        // exclude values that make denominator = 0
        List<Double> validRoots = new ArrayList<>();
        for (double root : candidateRoots) {
            if (Math.abs(evaluate(den, root)) > 1e-9) {
                validRoots.add(root);
            }
        }

        if (validRoots.isEmpty()) {
            return new Solution(new double[0], EquationType.RATIONAL, false, false);
        }

        double[] roots = validRoots.stream().mapToDouble(Double::doubleValue).toArray();
        return Solution.of(roots, EquationType.RATIONAL);
    }

    // delegates to the appropriate solver based on numerator degree
    private double[] solvePolynomial(String raw, double[] coeffs) {
        Equation eq = switch (coeffs.length - 1) {
            case 2 -> new QuadraticEquation(raw, coeffs[0], coeffs[1], coeffs[2]);
            case 3 -> new CubicEquation(raw, coeffs[0], coeffs[1], coeffs[2], coeffs[3]);
            case 4 -> new QuarticEquation(raw, coeffs[0], coeffs[1], coeffs[2], coeffs[3], coeffs[4]);
            default -> throw new IllegalArgumentException("Unsupported numerator degree.");
        };

        EquationSolver solver = switch (coeffs.length - 1) {
            case 2 -> new QuadraticSolver();
            case 3 -> new CubicSolver();
            case 4 -> new QuarticSolver();
            default -> throw new IllegalArgumentException("Unsupported numerator degree.");
        };

        return solver.solve(eq).getRoots();
    }

    // evaluates a polynomial at a given x
    private double evaluate(double[] coeffs, double x) {
        double result = 0;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * Math.pow(x, coeffs.length - 1 - i);
        }
        return result;
    }
}