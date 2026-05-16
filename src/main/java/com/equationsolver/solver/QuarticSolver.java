package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.CubicEquation;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.QuadraticEquation;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves quartic equations of the form {@code ax⁴ + bx³ + cx² + dx + e = 0}
 * using Ferrari's method via a resolvent cubic.
 *
 * <p>Algorithm steps:
 * <ol>
 *   <li>Normalize: divide all coefficients by {@code a}.</li>
 *   <li>Depress: substitute {@code x = t - b/4} to eliminate the x³ term.</li>
 *   <li>Biquadratic shortcut: if the linear term {@code |dq| ≈ 0}, substitute
 *       {@code y = t²} and solve the resulting quadratic in {@code y} directly.</li>
 *   <li>Otherwise, solve the resolvent cubic to find a value {@code m}, then
 *       split the quartic into two quadratics and solve each.</li>
 *   <li>Shift all roots back by {@code b/4}.</li>
 * </ol>
 *
 * <p>Internally delegates to {@link CubicSolver} and {@link QuadraticSolver}.
 */
public class QuarticSolver extends EquationSolver {

    private final CubicSolver cubicSolver = new CubicSolver();
    private final QuadraticSolver quadraticSolver = new QuadraticSolver();

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.QUARTIC) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        double[] c = equation.getCoefficients();
        double a = c[0], b = c[1], p = c[2], q = c[3], e = c[4];

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: ax⁴ + bx³ + cx² + dx + e = 0");
        steps.add("a=" + a + ", b=" + b + ", c=" + p + ", d=" + q + ", e=" + e);

        // normalize so leading coefficient is 1
        b /= a; p /= a; q /= a; e /= a;
        steps.add("Normalize: divide all by a");

        // depress: substitute x = t - b/4
        double shift = b / 4.0;
        double dp = p - 6 * shift * shift;
        double dq = q + 2 * p * shift - 8 * Math.pow(shift, 3);
        double dr = e - q * shift + p * shift * shift - 3 * Math.pow(shift, 4);
        steps.add("Depress via x = t - b/4,  shift=" + shift);

        // Biquadratic special case: when dq ≈ 0 the depressed quartic is t⁴ + dp·t² + dr = 0.
        // Substitute y = t² and solve the quadratic directly — the resolvent cubic approach
        // always gives inner = 2m + dp < 0 for symmetric biquadratics, producing no roots.
        if (Math.abs(dq) < 1e-10) {
            steps.add("|dq| ≈ 0: biquadratic — substitute y = t², solve y² + dp·y + dr = 0");
            Solution ySol = quadraticSolver.solve(
                    new QuadraticEquation(equation.getRawInput(), 1, dp, dr));
            List<Double> allRoots = new ArrayList<>();
            for (double y : ySol.getRoots()) {
                if (y >= -1e-10) {
                    double sqrtY = Math.sqrt(Math.max(0, y));
                    allRoots.add( sqrtY - shift);
                    allRoots.add(-sqrtY - shift);
                }
            }
            if (allRoots.isEmpty()) return Solution.noSolution(EquationType.QUARTIC, steps);
            double[] roots = allRoots.stream().mapToDouble(Double::doubleValue).toArray();
            steps.add("Roots: " + roots.length + " real root(s) found");
            return Solution.of(roots, EquationType.QUARTIC, steps);
        }

        // resolvent cubic
        double ca = 1;
        double cb = dp / 2.0;
        double cc = (dp * dp - 4 * dr) / 16.0;
        double cd = -(dq * dq) / 64.0;
        steps.add("Solve resolvent cubic: t³ + " + cb + "t² + " + cc + "t + " + cd + " = 0");

        Solution cubicSolution = cubicSolver.solve(
                new CubicEquation(equation.getRawInput(), ca, cb, cc, cd)
        );
        double m = cubicSolution.getRoots()[0];
        steps.add("Resolvent cubic root m=" + m);

        // split into two quadratics
        double inner    = 2 * m + dp;
        if (inner < 0) inner = 0;
        double sqrtInner = Math.sqrt(inner);

        double[] q1roots = quadraticSolver.solve(
                new QuadraticEquation(equation.getRawInput(), 1, sqrtInner, m + (Math.abs(sqrtInner) < 1e-10 ? 0 : dq / (2 * sqrtInner)))
        ).getRoots();

        double[] q2roots = quadraticSolver.solve(
                new QuadraticEquation(equation.getRawInput(), 1, -sqrtInner, m - (Math.abs(sqrtInner) < 1e-10 ? 0 : dq / (2 * sqrtInner)))
        ).getRoots();

        // collect and shift back all real roots
        List<Double> allRoots = new ArrayList<>();
        for (double r : q1roots) allRoots.add(r - shift);
        for (double r : q2roots) allRoots.add(r - shift);

        double[] roots = allRoots.stream().mapToDouble(Double::doubleValue).toArray();
        steps.add("Roots after back-substitution: " + roots.length + " real root(s) found");

        if (roots.length == 0) return Solution.noSolution(EquationType.QUARTIC, steps);
        return Solution.of(roots, EquationType.QUARTIC, steps);
    }
}