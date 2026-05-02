package com.equationsolver.solver;

import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.QuarticEquation;
import com.equationsolver.model.RawEquation;
import com.equationsolver.model.Solution;

public class QuarticSolver extends EquationSolver {

    private final CubicSolver cubicSolver = new CubicSolver();
    private final QuadraticSolver quadraticSolver = new QuadraticSolver();

    @Override
    protected void validate(Equation equation) {
        if (!(equation instanceof QuarticEquation)) {
            throw new IllegalArgumentException("Equation must be a QuarticEquation.");
        }
        double[] coeffs = equation.getCoefficients();
        if (coeffs[0] == 0) {
            throw new IllegalArgumentException("Leading coefficient 'a' cannot be zero.");
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        double[] c = equation.getCoefficients();
        double a = c[0], b = c[1], p = c[2], q = c[3], e = c[4];

        // normalize so leading coefficient is 1
        b /= a; p /= a; q /= a; e /= a;

        // step 1 — depress: substitute x = t - b/4
        double shift = b / 4.0;
        double dp = p - 6 * shift * shift;
        double dq = q + 2 * p * shift - 8 * shift * shift * shift;
        double dr = e - q * shift + p * shift * shift - 3 * shift * shift * shift * shift;

        // step 2 — resolvent cubic: m³ + (dp/2)m² + ((dp²−4dr)/16)m − dq²/64 = 0
        double ca = 1;
        double cb = dp / 2.0;
        double cc = (dp * dp - 4 * dr) / 16.0;
        double cd = -(dq * dq) / 64.0;

        Solution cubicSolution = cubicSolver.solve(
                new RawEquation(EquationType.CUBIC, ca, cb, cc, cd)
        );
        double m = cubicSolution.getRoots()[0];

        // step 3 — split into two quadratics and collect real roots
        double inner = 2 * m + dp;
        if (inner < 0) inner = 0;
        double sqrtInner = Math.sqrt(inner);

        double[] q1roots, q2roots;

        if (Math.abs(sqrtInner) < 1e-10) {
            q1roots = quadraticSolver.solve(
                    new RawEquation(EquationType.QUADRATIC, 1, 0, m)
            ).getRoots();
            q2roots = quadraticSolver.solve(
                    new RawEquation(EquationType.QUADRATIC, 1, 0, m)
            )