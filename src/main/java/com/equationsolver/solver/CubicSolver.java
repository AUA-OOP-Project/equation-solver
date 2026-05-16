package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves cubic equations of the form {@code ax³ + bx² + cx + d = 0}
 * using Cardano's method.
 *
 * <p>Algorithm steps:
 * <ol>
 *   <li>Normalize: divide all coefficients by {@code a}.</li>
 *   <li>Depress: substitute {@code x = t - b/3} to eliminate the x² term,
 *       yielding the depressed cubic {@code t³ + pt + q = 0}.</li>
 *   <li>Evaluate discriminant {@code Δ = -(4p³ + 27q²)}:
 *     <ul>
 *       <li>Δ &gt; 0 → three distinct real roots (trigonometric / Viète method)</li>
 *       <li>Δ = 0 → repeated real root</li>
 *       <li>Δ &lt; 0 → one real root, two complex conjugate roots (not returned)</li>
 *     </ul>
 *   </li>
 *   <li>Shift results back by {@code b/3}.</li>
 * </ol>
 */
public class CubicSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.CUBIC) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        double[] coefficients = equation.getCoefficients();
        double a = coefficients[0];
        double b = coefficients[1];
        double c = coefficients[2];
        double d = coefficients[3];

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: ax³ + bx² + cx + d = 0,  a=" + a + ", b=" + b + ", c=" + c + ", d=" + d);

        double[] pq    = toDepressedForm(a, b, c, d);
        double p       = pq[0];
        double q       = pq[1];
        steps.add("Depressed cubic t³ + pt + q = 0,  p=" + p + ", q=" + q);

        double delta   = computeDelta(p, q);
        steps.add("Discriminant Δ = " + delta);

        List<Double> tRoots;
        if (delta > 1e-9) {
            steps.add("Δ > 0  →  3 distinct real roots (trigonometric method)");
            tRoots = trigMethod(p, q);
        } else {
            steps.add("Δ ≤ 0  →  Cardano's formula");
            tRoots = cardano(p, q);
        }

        double[] roots = shiftBack(tRoots, a, b, c, d);
        steps.add("Back-substituted roots: " + formatRoots(roots));

        return Solution.of(roots, EquationType.CUBIC, steps);
    }

    private double[] toDepressedForm(double a, double b, double c, double d) {
        double p = (3 * a * c - b * b) / (3 * a * a);
        double q = (2 * Math.pow(b, 3) - 9 * a * b * c + 27 * a * a * d) / (27 * Math.pow(a, 3));
        return new double[]{p, q};
    }

    private double computeDelta(double p, double q) {
        return -(4 * Math.pow(p, 3) + 27 * Math.pow(q, 2));
    }

    private List<Double> trigMethod(double p, double q) {
        List<Double> roots = new ArrayList<>();
        double m     = 2 * Math.sqrt(-p / 3);
        double inner = (3 * q) / (2 * p) * Math.sqrt(-3.0 / p);
        inner        = Math.max(-1.0, Math.min(1.0, inner));
        double theta = Math.acos(inner) / 3.0;
        roots.add(m * Math.cos(theta));
        roots.add(m * Math.cos(theta - 2 * Math.PI / 3));
        roots.add(m * Math.cos(theta - 4 * Math.PI / 3));
        return roots;
    }

    private List<Double> cardano(double p, double q) {
        List<Double> roots = new ArrayList<>();
        double D = Math.pow(q / 2, 2) + Math.pow(p / 3, 3);
        double u = Math.cbrt(-q / 2 + Math.sqrt(Math.abs(D)));
        double v = Math.cbrt(-q / 2 - Math.sqrt(Math.abs(D)));
        roots.add(u + v);
        return roots;
    }

    private double[] shiftBack(List<Double> tRoots, double a, double b, double c, double d) {
        double shift   = b / (3 * a);
        double[] roots = new double[tRoots.size()];
        for (int i = 0; i < tRoots.size(); i++) {
            double x = tRoots.get(i) - shift;
            roots[i] = newtonRefine(x, a, b, c, d);
        }
        return roots;
    }

    private double newtonRefine(double x, double a, double b, double c, double d) {
        for (int i = 0; i < 10; i++) {
            double fx  = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
            double fpx = 3 * a * Math.pow(x, 2) + 2 * b * x + c;
            if (Math.abs(fpx) < 1e-12) break;
            x = x - fx / fpx;
        }
        return x;
    }

    private String formatRoots(double[] roots) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roots.length; i++) {
            sb.append("x").append(i + 1).append("=").append(String.format("%.4f", roots[i]));
            if (i < roots.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
}