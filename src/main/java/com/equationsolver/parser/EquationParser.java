package com.equationsolver.parser;

import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;

public interface EquationParser {
    Equation parse(String input);
    boolean supports(EquationType type);
}