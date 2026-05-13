package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.ExponentialEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExponentialEquationParser implements EquationParser {

    // matches: base^x = rhs  (e.g. "2^x = 8", "e^x = 5", "10^x = 100")
    private static final Pattern PATTERN = Pattern.compile(
            "([0-9e.]+)\\^x\\s*=\\s*(-?[0-9.]+)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.EXPONENTIAL;
    }

    @Override
    public Equation parse(String input) {
        String normalized = input.replaceAll("\\s+", "");

        Matcher m = PATTERN.matcher(normalized);
        if (!m.find()) throw new InvalidEquationException(input);

        double base = parseBase(m.group(1), input);
        double rhs  = Double.parseDouble(m.group(2));

        return new ExponentialEquation(input, base, rhs);
    }

    // handles "e" or "E" as Euler's number, otherwise parses as double
    private double parseBase(String baseStr, String originalInput) {
        if (baseStr.equalsIgnoreCase("e")) return Math.E;
        try {
            return Double.parseDouble(baseStr);
        } catch (NumberFormatException ex) {
            throw new InvalidEquationException(originalInput, ex);
        }
    }
}