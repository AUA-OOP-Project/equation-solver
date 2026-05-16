package com.equationsolver.model;

import com.equationsolver.display.Displayable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Immutable value object that holds the result of solving an equation.
 *
 * <p>A solution can represent one of three outcomes:
 * <ul>
 *   <li><b>Finite roots</b> – one or more real roots stored in the roots array.</li>
 *   <li><b>No solution</b> – the equation has no real roots (e.g., discriminant &lt; 0).</li>
 *   <li><b>Infinite solutions</b> – the equation is an identity (e.g., 0 = 0).</li>
 * </ul>
 *
 * <p>Use the static factory methods to create instances:
 * <ul>
 *   <li>{@link #of(double[], EquationType)} / {@link #of(double[], EquationType, List)}</li>
 *   <li>{@link #noSolution(EquationType)} / {@link #noSolution(EquationType, List)}</li>
 *   <li>{@link #infiniteSolutions(EquationType)} / {@link #infiniteSolutions(EquationType, List)}</li>
 * </ul>
 *
 * <p>Solvers may optionally attach step-by-step explanations via the {@code steps} list,
 * accessible through {@link #getSteps()} and rendered by {@link #displayWithSteps()}.
 */
public class Solution implements Displayable {

    private final double[] roots;
    private final EquationType type;
    private final boolean hasSolution;
    private final boolean infiniteSolutions;
    private final List<String> steps;

    private Solution(double[] roots, EquationType type, boolean hasSolution, boolean infiniteSolutions, List<String> steps) {
        this.roots             = roots;
        this.type              = type;
        this.hasSolution       = hasSolution;
        this.infiniteSolutions = infiniteSolutions;
        // defensive copy + unmodifiable at construction time, not in getter
        this.steps             = Collections.unmodifiableList(steps != null ? new ArrayList<>(steps) : new ArrayList<>());
    }

    // ── factory methods ──────────────────────────────────────────────────────

    public static Solution of(double[] roots, EquationType type) {
        return new Solution(roots, type, true, false, null);
    }

    public static Solution of(double[] roots, EquationType type, List<String> steps) {
        return new Solution(roots, type, true, false, steps);
    }

    public static Solution noSolution(EquationType type) {
        return new Solution(new double[0], type, false, false, null);
    }

    public static Solution noSolution(EquationType type, List<String> steps) {
        return new Solution(new double[0], type, false, false, steps);
    }

    public static Solution infiniteSolutions(EquationType type) {
        return new Solution(new double[0], type, false, true, null);
    }

    public static Solution infiniteSolutions(EquationType type, List<String> steps) {
        return new Solution(new double[0], type, false, true, steps);
    }

    // ── getters ──────────────────────────────────────────────────────────────

    public double[] getRoots() {
        return Arrays.copyOf(roots, roots.length);
    }

    public EquationType getType() {
        return type;
    }

    public boolean hasSolution() {
        return hasSolution;
    }

    public boolean isInfiniteSolutions() {
        return infiniteSolutions;
    }

    public List<String> getSteps() {
        return steps;
    }

    // ── display ──────────────────────────────────────────────────────────────

    @Override
    public String display() {
        if (infiniteSolutions) return "Infinite solutions";
        if (!hasSolution)      return "No real solutions";
        if (roots.length == 1) return "x = " + formatRoot(roots[0]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roots.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append("x").append(toSubscript(i + 1)).append(" = ").append(formatRoot(roots[i]));
        }
        return sb.toString();
    }

    public String displayWithSteps() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < steps.size(); i++) {
            sb.append("Step ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
        }
        sb.append("Result: ").append(display());
        return sb.toString();
    }

    private String formatRoot(double root) {
        return root == (long) root ? String.valueOf((long) root) : String.valueOf(root);
    }

    private String toSubscript(int n) {
        return switch (n) {
            case 1 -> "₁"; case 2 -> "₂"; case 3 -> "₃"; case 4 -> "₄";
            default -> String.valueOf(n);
        };
    }

    @Override
    public String toString() {
        return display();
    }
}