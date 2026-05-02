package com.equationsolver.parser;

import com.equationsolver.exception.InvalidEquationException;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.LogarithmicEquation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogarithmicEquationParser implements EquationParser {

    // log2(x) = 3   or   log_2(x) = 3
    private static final Pattern LOG_BASE_PATTERN = Pattern.compile(
            "log_?([0-9.]+)\\(x\\)\\s*=\\s*(-?[0-9.]+)",
            Pattern.CASE_INSENSITIVE
    );

    // log(x) = 2  →  base 10
    private static final Pattern LOG10_PATTERN = Pattern.compile(
            "log\\(x\\)\\s*=\\s*(-?[0-9.]+)",
            Pattern.CASE_INSENSITIVE
    );

    // ln(x) = 1  →  base e
    private static final Pattern LN_PATTERN = Pattern.compile(
            "ln\\(x\\)\\s*=\\s*(-?[0-9.]+)",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean supports(EquationType type) {
        return type == EquationType.LOGARITHMIC;
    }

    @Override
    public Equation parse(String input) {
        String normalized = input.replaceAll("\\s+", "");

        // ln(x) = rhs  →  base e
        Matcher ln = LN_PATTERN.matcher(normalized);
        if (ln.find()) {
            return new LogarithmicEquation(input, Math.E, Double.parseDouble(ln.group(1)));
        }

        // log(x) = rhs  →  base 10
        Matcher log10 = LOG10_PATTERN.matcher(normalized);
        if (log10.find()) {
            return new LogarithmicEquation(input, 10, Double.parseDouble(log10.group(1)));
        }

        // log_base(x) = rhs  or  logBase(x) = rhs
        Matcher logBase = LOG_BASE_PATTERN.matcher(normalized);
        if (logBase.find()) {
            double base = Double.parseDouble(logBase.group(1));
            double rhs  = Double.parseDouble(logBase.group(2));
            return new LogarithmicEquation(input, base, rhs);
        }

        throw new InvalidEquationException(input);
    }
}