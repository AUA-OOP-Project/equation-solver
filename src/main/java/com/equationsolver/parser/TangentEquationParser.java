package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.TangentEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TangentEquationParser implements EquationParser {

    private static final Pattern PATTERN = Pattern.compile(
            "tan\\s*\\(\\s*x\\s*\\)\\s*=\\s*([+-]?\\d*\\.?\\d+)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean supports(String input) {
        return input.toLowerCase().contains("tan");
    }

    @Override
    public Equation parse(String input) {
        String raw = input.trim();
        Matcher m = PATTERN.matcher(raw);
        if (!m.find()) {
            throw new InvalidEquationException("Invalid tangent equation format. Expected: tan(x) = value");
        }
        double rhs = Double.parseDouble(m.group(1));
        return new TangentEquation(raw, rhs);
    }
}