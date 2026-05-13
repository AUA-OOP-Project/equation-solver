package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class LinearSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.LINEAR) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        double a = equation.getCoefficients()[0];
        double b = equation.getCoefficients()[1];

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: ax + b = 0,  a=" + format(a) + ", b=" + format(b));

        if (a == 0 && b == 0) {
            steps.add("0x + 0 = 0  →  0 = 0  →  true for all x");
            return Solution.infiniteSolutions(EquationType.LINEAR, steps);
        }

        if (a == 0) {
            steps.add("0x + " + format(b) + " = 0  →  " + format(b) + " = 0  →  contradiction");
            return Solution.noSolution(EquationType.LINEAR, steps);
        }

        steps.add("x = -b / a");
        steps.add("x = -(" + format(b) + ") / " + format(a));
        double x = -b / a;
        steps.add("x = " + format(x));

        return Solution.of(new double[]{x}, EquationType.LINEAR, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}