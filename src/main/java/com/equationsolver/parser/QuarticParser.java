package com.equationsolver.parser;

import com.equationsolver.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationParser {

    public Equation parse(String input) {
        String raw = input.trim();
        String normalized = normalize(raw);
        int degree = detectDegree(normalized);

        double[] coefficients = extractCoefficients(normalized, degree);

        return switch (degree) {
            case 2 -> new QuadraticEquation(raw, coefficients[0], coefficients[1], coefficients[2]);
            case 3 -> new CubicEquation(raw, coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
            case 4 -> new QuarticEquation(raw, coefficients[0], coefficients[1], coefficients[2], coefficients[3], coefficients[4]);
            default -> throw new IllegalArgumentException("Unsupported degree: " + degree + ". Only degrees 2, 3 and 4 are supported.");
        };
    }

    // lowercase, remove spaces, strip '= 0' from the end
    private String normalize(String input) {
        String s = input.toLowerCase().replaceAll("\\s+", "");
        if (s.endsWith("=0")) s = s.substring(0, s.length() - 2);
        return s;
    }

    // find the highest power of x present
    private int detectDegree(String input) {
        int degree = 0;
        Matcher m = Pattern.compile("x\\^(\\d+)").matcher(input);
        while (m.find()) {
            int power = Integer.parseInt(m.group(1));
            if (power > degree) degree = power;
        }
        // check for plain x (degree 1 term) and x with no caret
        if (degree == 0 && input.contains("x")) degree = 1;
        return degree;
    }

    // extract coefficients from highest to lowest degree
    private double[] extractCoefficients(String input, int degree) {
        double[] coeffs = new double[degree + 1];

        for (int power = degree; power >= 0; power--) {
            coeffs[degree - power] = extractCoefficient(input, power);
        }

        return coeffs;
    }

    private double extractCoefficient(String input, int power) {
        String pattern;

        if (power == 0) {
            // constant term: a number not followed by x
            pattern = "([+-]?\\d*\\.?\\d+)(?![\\.\\d]*x)(?![\\^\\d])";
        } else if (power == 1) {
            // x or nx
            pattern = "([+-]?\\d*\\.?\\d*)x(?!\\^)";
        } else {
            // x^n or nx^n
            pattern = "([+-]?\\d*\\.?\\d*)x\\^" + power;
        }

        Matcher m = Pattern.compile(pattern).matcher(input);

        double coeff = 0;
        while (m.find()) {
            String captured = m.group(1);
            if (captured.isEmpty() || captured.equals("+")) coeff += 1;
            else if (captured.equals("-"))                   coeff -= 1;
            else                                             coeff += Double.parseDouble(captured);
        }

        return coeff;
    }
}