package com.equationsolver.parser;

import com.equationsolver.exception.UnsupportedEquationTypeException;
import com.equationsolver.model.EquationType;

public class ParserFactory {

    public static EquationType detectType(String input) {
        String normalized = input.replaceAll("\\s+", "").toLowerCase();

        if (normalized.startsWith("ln("))                             return EquationType.LOGARITHMIC;
        if (normalized.startsWith("log"))                             return EquationType.LOGARITHMIC;
        if (normalized.startsWith("sin("))                            return EquationType.SINE;
        if (normalized.startsWith("cos("))                            return EquationType.COSINE;
        if (normalized.startsWith("tan("))                            return EquationType.TANGENT;
        if (normalized.contains("^x"))                                return EquationType.EXPONENTIAL;
        if (normalized.contains("|"))                                 return EquationType.ABSOLUTE_VALUE;
        if (normalized.contains("/") && normalized.contains("("))     return EquationType.RATIONAL;
        if (normalized.contains("x^4") || normalized.contains("x⁴")) return EquationType.QUARTIC;
        if (normalized.contains("x^3") || normalized.contains("x³")) return EquationType.CUBIC;
        if (normalized.contains("x^2") || normalized.contains("x²")) return EquationType.QUADRATIC;
        return EquationType.LINEAR;
    }

    public static EquationParser getParser(EquationType type) {
        return switch (type) {
            case LINEAR          -> new LinearEquationParser();
            case QUADRATIC       -> new QuadraticEquationParser();
            case CUBIC           -> new CubicEquationParser();
            case QUARTIC         -> new QuarticEquationParser();
            case EXPONENTIAL     -> new ExponentialEquationParser();
            case LOGARITHMIC     -> new LogarithmicEquationParser();
            case RATIONAL        -> new RationalEquationParser();
            case ABSOLUTE_VALUE  -> new AbsoluteValueEquationParser();
            case SINE            -> new SineEquationParser();
            case COSINE          -> new CosineEquationParser();
            case TANGENT         -> new TangentEquationParser();
            default              -> throw new UnsupportedEquationTypeException(type);
        };
    }
}