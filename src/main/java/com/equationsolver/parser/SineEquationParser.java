package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.SineEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses sine equations of the form {@code sin(x) = rhs} (case-insensitive).
 */
public class SineEquationParser implements EquationParser {

    private static final Pattern SINE_PATTERN = Pattern.compile(
            "sin\\(x\\)\\s*=\\s*(-?\\d+\\.?\\d*)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.SINE;
    }

    @Override
    public Equation parse(String input) {
        String normalized = input.replaceAll("\\s+", "");
        Matcher m = SINE_PATTERN.matcher(normalized);
        if (!m.find()) {
            throw new InvalidEquationException(input);
        }
        double rhs = Double.parseDouble(m.group(1));
        return new SineEquation(input, rhs);
    }
}
