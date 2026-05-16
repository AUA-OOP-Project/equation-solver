package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.QuarticEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses quartic equations of the form {@code ax⁴ + bx³ + cx² + dx + e = 0}.
 *
 * <p>Uses a regex pattern to scan the left-hand side for {@code x^4}, {@code x^3},
 * {@code x^2}, {@code x}, and constant terms. Terms may appear in any order.
 */
public class QuarticEquationParser implements EquationParser {

    private static final Pattern TERM_PATTERN = Pattern.compile(
            "([+-]?\\d*\\.?\\d*)x\\^4|" +
                    "([+-]?\\d*\\.?\\d*)x\\^3|" +
                    "([+-]?\\d*\\.?\\d*)x\\^2|" +
                    "([+-]?\\d*\\.?\\d*)x(?!\\^)|" +
                    "([+-]?\\d+\\.?\\d*)"
    );

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.QUARTIC;
    }

    @Override
    public Equation parse(String input) {
        String raw      = input.trim();
        String leftSide = raw.contains("=") ? raw.split("=")[0].trim() : raw;
        String cleaned  = leftSide.replaceAll("\\s+", "");

        double a = 0, b = 0, c = 0, d = 0, e = 0;

        Matcher matcher = TERM_PATTERN.matcher(cleaned);
        while (matcher.find()) {
            if      (matcher.group(1) != null) a = parseCoefficient(matcher.group(1));
            else if (matcher.group(2) != null) b = parseCoefficient(matcher.group(2));
            else if (matcher.group(3) != null) c = parseCoefficient(matcher.group(3));
            else if (matcher.group(4) != null) d = parseCoefficient(matcher.group(4));
            else if (matcher.group(5) != null) e = Double.parseDouble(matcher.group(5));
        }

        if (a == 0) throw new InvalidEquationException(input);

        return new QuarticEquation(raw, a, b, c, d, e);
    }

    private double parseCoefficient(String s) {
        if (s.isEmpty() || s.equals("+")) return  1.0;
        if (s.equals("-"))               return -1.0;
        return Double.parseDouble(s);
    }
}