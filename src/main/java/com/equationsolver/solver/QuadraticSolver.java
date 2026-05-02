package com.equationsolver.solver;

import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;

public class QuadraticSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (!(equation instanceof com.equationsolver.model.QuadraticEquation)) {
            throw new IllegalArgumentException("Expected a QuadraticEquation.");
        }
        double[] coeffs = equation.getCoefficients();
        if (coeffs[0] == 0) {
            throw new IllegalArgumentException("Leading coefficient 'a' cannot be zero.");
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        double[] c = equation.getCoefficients();
        double a = c[0], b = c[1], cc = c[2];

        double discriminant = b * b - 4 * a * cc;

        if (discriminant < 0) {
            return new Solution(new double[0], EquationType.QUADRATIC, false, false);
        }

        if (discriminant == 0) {
            double root = -b / (2 * a);
            return new Solution(new double[]{ root }, EquationType.QUADRATIC, true, false);
        }

        double sqrtDisc = Math.sqrt(discriminant);
        double root1 = (-b + sqrtDisc) / (2 * a);
        double root2 = (-b - sqrtDisc) / (2 * a);
        return new Solution(new double[]{ root1, root2 }, EquationType.QUADRATIC, true, false);
    }

    public double[] solveCoeffs(double a, double b, double c) {
        return doSolve(new com.equationsolver.model.RawEquation(EquationType.QUADRATIC, a, b, c)).getRoots();
    }
}