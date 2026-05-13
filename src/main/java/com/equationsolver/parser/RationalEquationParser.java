package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.RationalEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RationalEquationParser implements EquationParser {

    // matches: (numerator) / (denominator) with optional = 0
    private static final Pattern RATIONAL_PATTERN =
            Pattern.compile("\\(([^)]+)\\)\\s*/\\s*\\(([^)]+)\\)");

    // matches polynomial terms: ax^n, bx, c
    private static final Pattern TERM_PATTERN = Pattern.compile(
            "([+-]?\\d*\\.?\\d*)x\\^(\\d+)|([+-]?\\d*\\.?\\d*)x|([+-]?\\d+\\.?\\d*)");

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.RATIONAL;
    }

    @Override
    public Equation parse(String input) {
        try {
            String lhs = stripRhs(input.trim());
            Matcher m = RATIONAL_PATTERN.matcher(lhs);
            if (!m.find()) {
                throw new InvalidEquationException(input);
            }

            String numStr = m.group(1).trim();
            String denStr = m.group(2).trim();

            double[] numerator   = parsePolynomial(numStr);
            double[] denominator = parsePolynomial(denStr);

            return new RationalEquation(input, numerator, denominator);
        } catch (InvalidEquationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEquationException(input, e);
        }
    }

    private String stripRhs(String input) {
        int eqIdx = input.lastIndexOf('=');
        if (eqIdx >= 0) {
            return input.substring(0, eqIdx).trim();
        }
        return input;
    }

    // parses "x^2 - 3x + 2" → [1, -3, 2]
    private double[] parsePolynomial(String expr) {
        String cleaned = expr.replaceAll("\\s+", "");
        int degree = detectDegree(cleaned);
        double[] coeffs = new double[degree + 1];

        Matcher m = TERM_PATTERN.matcher(cleaned);
        while (m.find()) {
            if (m.group(1) != null && m.group(2) != null) {
                int power = Integer.parseInt(m.group(2));
                coeffs[degree - power] += parseCoefficient(m.group(1));
            } else if (m.group(3) != null) {
                coeffs[degree - 1] += parseCoefficient(m.group(3));
            } else if (m.group(4) != null) {
                coeffs[degree] += Double.parseDouble(m.group(4));
            }
        }

        return coeffs;
    }

    private int detectDegree(String expr) {
        int max = 0;
        Matcher m = Pattern.compile("x\\^(\\d+)").matcher(expr);
        while (m.find()) {
            max = Math.max(max, Integer.parseInt(m.group(1)));
        }
        if (max == 0 && expr.contains("x")) max = 1;
        return max;
    }

    private double parseCoefficient(String s) {
        if (s.isEmpty() || s.equals("+")) return  1.0;
        if (s.equals("-"))               return -1.0;
        return Double.parseDouble(s);
    }
}