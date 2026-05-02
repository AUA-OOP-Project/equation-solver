package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.ExponentialEquation;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class ExponentialSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.EXPONENTIAL) {
            throw new InvalidEquationException(equation.getRawInput());
        }
        ExponentialEquation eq = (ExponentialEquation) equation;
        if (eq.getBase() <= 0 || eq.getBase() == 1) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        ExponentialEquation eq = (ExponentialEquation) equation;
        double base = eq.getBase();
        double rhs  = eq.getRhs();

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: base^x = rhs,  base=" + format(base) + ", rhs=" + format(rhs));

        if (rhs <= 0) {
            steps.add("rhs ≤ 0  →  exponential is always positive, no real solution");
            return Solution.noSolution(EquationType.EXPONENTIAL, steps);
        }

        steps.add("Take log of both sides: x·log(" + format(base) + ") = log(" + format(rhs) + ")");
        steps.add("x = log(rhs) / log(base)");
        double x = Math.log(rhs) / Math.log(base);
        steps.add("x = log(" + format(rhs) + ") / log(" + format(base) + ") = " + format(x));

        return Solution.of(new double[]{x}, EquationType.EXPONENTIAL, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}