package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.AbsoluteValueEquation;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Parses: |ax + b| = c
public class AbsoluteValueEquationParser implements EquationParser {

    // captures everything inside |...| and the rhs after '='
    private static final Pattern ABS_PATTERN = Pattern.compile(
            "\\|([^|]+)\\|\\s*=\\s*(-?\\d+\\.?\\d*)");

    // extracts coefficient of x: "2x", "-x", "x", "+3x"
    private static final Pattern X_PATTERN = Pattern.compile(
            "([+-]?\\d*\\.?\\d*)x");

    // extracts standalone constant not attached to x
    private static final Pattern CONST_PATTERN = Pattern.compile(
            "([+-]?\\d+\\.?\\d*)(?![x\\d])");

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.ABSOLUTE_VALUE;
    }

    @Override
    public Equation parse(String input) {
        try {
            String normalized = input.replaceAll("\\s+", "");
            Matcher m = ABS_PATTERN.matcher(normalized);
            if (!m.find()) {
                throw new InvalidEquationException(input);
            }

            String inner = m.group(1);   // e.g. "2x+3"
            double c     = Double.parseDouble(m.group(2));

            double a = extractA(inner, input);
            double b = extractB(inner);

            return new AbsoluteValueEquation(input, a, b, c);
        } catch (InvalidEquationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEquationException(input, e);
        }
    }

    private double extractA(String inner, String raw) {
        Matcher m = X_PATTERN.matcher(inner);
        if (!m.find()) throw new InvalidEquationException(raw);
        String coeff = m.group(1);
        if (coeff.isEmpty() || coeff.equals("+")) return  1.0;
        if (coeff.equals("-"))                     return -1.0;
        return Double.parseDouble(coeff);
    }

    private double extractB(String inner) {
        String withoutX = inner.replaceAll("[+-]?\\d*\\.?\\d*x", "");
        if (withoutX.isEmpty()) return 0;
        Matcher m = CONST_PATTERN.matcher(withoutX);
        if (!m.find()) return 0;
        return Double.parseDouble(m.group(1));
    }
}
