package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.LinearEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinearEquationParser implements EquationParser {

    // matches the x coefficient: optional sign, optional number, then 'x'
    private static final Pattern X_PATTERN = Pattern.compile("([+-]?\\d*\\.?\\d*)x");

    // matches a standalone number not attached to x
    private static final Pattern CONST_PATTERN = Pattern.compile("([+-]?\\d+\\.?\\d*)(?![x\\d])");

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.LINEAR;
    }

    @Override
    public Equation parse(String input) {
        try {
            String expr = normalize(input);
            double a = extractA(expr);
            double b = extractB(expr);
            return new LinearEquation(input, a, b);
        } catch (InvalidEquationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEquationException(input, e);
        }
    }

    // "2x + 4 = 0"  →  "2x+4"
    // "2x = -4"     →  "2x+4"  (moves rhs to left, flips sign)
    private String normalize(String input) {
        String[] sides = input.split("=");
        if (sides.length != 2) throw new InvalidEquationException(input);

        String left  = sides[0].replaceAll("\\s+", "");
        String right = sides[1].replaceAll("\\s+", "");

        // if rhs is not 0, subtract it: move to left side with flipped sign
        if (!right.equals("0")) {
            double rhs = Double.parseDouble(right);
            if (rhs > 0) left = left + "+" + (-rhs);
            if (rhs < 0) left = left + "+" + (-rhs);
        }

        return left;
    }

    // extracts coefficient of x from "2x+4" → 2.0
    // handles: "x" → 1,  "-x" → -1,  "2x" → 2,  "-2x" → -2
    private double extractA(String expr) {
        Matcher m = X_PATTERN.matcher(expr);
        if (!m.find()) throw new InvalidEquationException(expr);

        String coeff = m.group(1);
        if (coeff.isEmpty() || coeff.equals("+")) return 1;
        if (coeff.equals("-"))                      return -1;
        return Double.parseDouble(coeff);
    }

    // extracts constant from "2x+4" → 4.0
    // if no constant found, defaults to 0
    private double extractB(String expr) {
        // remove the x term first so we don't match its digits
        String withoutX = expr.replaceAll("[+-]?\\d*\\.?\\d*x", "");
        if (withoutX.isEmpty()) return 0;

        Matcher m = CONST_PATTERN.matcher(withoutX);
        if (!m.find()) return 0;
        return Double.parseDouble(m.group(1));
    }
}