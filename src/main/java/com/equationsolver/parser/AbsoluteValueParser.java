package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.AbsoluteValueEquation;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbsoluteValueParser implements EquationParser {

    private static final Pattern ABS_PATTERN = Pattern.compile(
            "\\|([^|]+)\\)([+-]?\\d+\\.?\\d*)?=([+-]?\\d+\\.?\\d*)");


    private static final Pattern INNER_PATTERN = Pattern.compile(
            "([+-]?\\d*\\.?\\d*)x([+-]\\d+\\.?\\d*)?");

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.ABSOLUTE_VALUE;
    }

    @Override
    public Equation parse(String input) {
        try {
            String raw     = input.trim();
            String cleaned = raw.replaceAll("\\s+", "");


            int pipeOpen  = cleaned.indexOf('|');
            int pipeClose = cleaned.indexOf('|', pipeOpen + 1);

            if (pipeOpen == -1 || pipeClose == -1) {
                throw new InvalidEquationException(input);
            }

            String inner   = cleaned.substring(pipeOpen + 1, pipeClose);
            String outer   = cleaned.substring(pipeClose + 1);


            double a = 0, b = 0;
            Matcher innerMatcher = INNER_PATTERN.matcher(inner);
            if (innerMatcher.find()) {
                a = parseCoefficient(innerMatcher.group(1));
                b = innerMatcher.group(2) != null
                        ? Double.parseDouble(innerMatcher.group(2))
                        : 0;
            } else {
                throw new InvalidEquationException(input);
            }


            if (!outer.contains("=")) {
                throw new InvalidEquationException(input);
            }

            String[] sides = outer.split("=");
            double c = sides[0].isEmpty() ? 0 : Double.parseDouble(sides[0]);
            double d = Double.parseDouble(sides[1]);

            return new AbsoluteValueEquation(raw, a, b, c, d);

        } catch (InvalidEquationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEquationException(input, e);
        }
    }

    private double parseCoefficient(String s) {
        if (s == null || s.isEmpty() || s.equals("+")) return  1.0;
        if (s.equals("-"))                              return -1.0;
        return Double.parseDouble(s);
    }
}