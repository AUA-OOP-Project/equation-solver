package com.equationsolver.model;

import com.equationsolver.display.Displayable;
import java.util.Arrays;

public class Solution implements Displayable {

    private final double[] roots;
    private final EquationType type;
    private final boolean hasSolution;
    private final boolean infiniteSolutions;

    private Solution(double[] roots, EquationType type, boolean hasSolution, boolean infiniteSolutions) {
        this.roots = roots;
        this.type = type;
        this.hasSolution = hasSolution;
        this.infiniteSolutions = infiniteSolutions;
    }

    public static Solution of(double[] roots, EquationType type) {
        return new Solution(roots, type, true, false);
    }

    public static Solution noSolution(EquationType type) {
        return new Solution(new double[0], type, false, false);
    }

    public static Solution infiniteSolutions(EquationType type) {
        return new Solution(new double[0], type, false, true);
    }

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

    @Override
    public String display() {
        if (infiniteSolutions) {
            return "Infinite solutions";
        }
        if (!hasSolution) {
            return "No real solutions";
        }
        if (roots.length == 1) {
            return "x = " + formatRoot(roots[0]);
        }
        return "x₁ = " + formatRoot(roots[0]) + ", x₂ = " + formatRoot(roots[1]);
    }

    private String formatRoot(double root) {
        return root == (long) root ? String.valueOf((long) root) : String.valueOf(root);
    }

    @Override
    public String toString() {
        return display();
    }
}