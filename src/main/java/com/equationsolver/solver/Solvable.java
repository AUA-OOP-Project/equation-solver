package com.equationsolver.solver;

import com.equationsolver.model.Equation;
import com.equationsolver.model.Solution;

public interface Solvable {
    Solution solve(Equation equation);
}