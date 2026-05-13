package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.CosineEquation;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Parses: cos(x) = rhs
public class CosineEquationParser implements EquationParser {

    private static final Pattern COSINE_PATTERN = Pattern.compile(
            "cos\\(x\\)\\s*=\\s*(-?\\d+\\.?\\d*)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.COSINE;
    }

    @Override
    public Equation parse(String input) {
        String normalized = input.replaceAll("\\s+", "");
        Matcher m = COSINE_PATTERN.matcher(normalized);
        if (!m.find()) {
            throw new InvalidEquationException(input);
        }
        double rhs = Double.parseDouble(m.group(1));
        return new CosineEquation(input, rhs);
    }
}
