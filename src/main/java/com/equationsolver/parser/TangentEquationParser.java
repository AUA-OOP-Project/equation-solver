package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.TangentEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Parses: tan(x) = rhs
public class TangentEquationParser implements EquationParser {

    private static final Pattern TANGENT_PATTERN = Pattern.compile(
            "tan\\(x\\)\\s*=\\s*(-?\\d+\\.?\\d*)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.TANGENT;
    }

    @Override
    public Equation parse(String input) {
        String normalized = input.replaceAll("\\s+", "");
        Matcher m = TANGENT_PATTERN.matcher(normalized);
        if (!m.find()) {
            throw new InvalidEquationException(input);
        }
        double rhs = Double.parseDouble(m.group(1));
        return new TangentEquation(input, rhs);
    }
}
