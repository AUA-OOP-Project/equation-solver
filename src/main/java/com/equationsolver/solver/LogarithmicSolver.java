package com.equationsolver.solver;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.LogarithmicEquation;
import com.equationsolver.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class LogarithmicSolver extends EquationSolver {

    @Override
    protected void validate(Equation equation) {
        if (equation.getType() != EquationType.LOGARITHMIC) {
            throw new InvalidEquationException(equation.getRawInput());
        }
        LogarithmicEquation eq = (LogarithmicEquation) equation;
        if (eq.getBase() <= 0 || eq.getBase() == 1) {
            throw new InvalidEquationException(equation.getRawInput());
        }
    }

    @Override
    protected Solution doSolve(Equation equation) {
        LogarithmicEquation eq = (LogarithmicEquation) equation;
        double base = eq.getBase();
        double rhs  = eq.getRhs();

        List<String> steps = new ArrayList<>();
        steps.add("Standard form: log_base(x) = rhs,  base=" + format(base) + ", rhs=" + format(rhs));
        steps.add("Convert: x = base^rhs");
        steps.add("x = " + format(base) + "^" + format(rhs));

        double x = Math.pow(base, rhs);
        steps.add("x = " + format(x));

        return Solution.of(new double[]{x}, EquationType.LOGARITHMIC, steps);
    }

    private String format(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }
}