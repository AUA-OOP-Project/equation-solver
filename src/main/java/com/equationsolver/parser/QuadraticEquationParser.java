package com.equationsolver.parser;

import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.QuadraticEquation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuadraticEquationParser implements EquationParser {

    private static final Pattern TERM_PATTERN = Pattern.compile(
            "([+-]?\\d*\\.?\\d*)x\\^2|" + "([+-]?\\d*\\.?\\d*)x|" + "([+-]?\\d+\\.?\\d*)");

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.QUADRATIC;
    }


    @Override
    public Equation parse(String input) {
        String raw      = input.trim();
        String leftSide = raw.contains("=") ? raw.split("=")[0].trim() : raw;


        String cleaned  = leftSide.replaceAll("\\s+", "");

        double a = 0, b = 0, c = 0;

        Matcher matcher = TERM_PATTERN.matcher(cleaned);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                a = parseCoefficient(matcher.group(1));
            } else if (matcher.group(2) != null) {
                b = parseCoefficient(matcher.group(2));
            } else if (matcher.group(3) != null) {
                c = Double.parseDouble(matcher.group(3));
            }
        }

        if (a == 0) {
            throw new IllegalArgumentException(
                    "Not a quadratic equation — coefficient 'a' is zero: " + input
            );
        }

        return new QuadraticEquation(raw, a, b, c);
    }


    private double parseCoefficient(String s) {
        if (s.isEmpty() || s.equals("+")) return  1.0;
        if (s.equals("-"))               return -1.0;
        return Double.parseDouble(s);
    }
}