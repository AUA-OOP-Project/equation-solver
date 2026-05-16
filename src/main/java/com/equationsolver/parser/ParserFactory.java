package com.equationsolver.parser;

import com.equationsolver.exception.UnsupportedEquationTypeException;
import com.equationsolver.model.EquationType;

/**
 * Factory that detects the type of a raw equation string and instantiates the
 * appropriate {@link EquationParser}.
 *
 * <p>Two static methods cover the full parsing pipeline:
 * <ol>
 *   <li>{@link #detectType(String)} – inspects the input string and returns the
 *       matching {@link EquationType} using pattern-priority rules.</li>
 *   <li>{@link #getParser(EquationType)} – returns a fresh parser instance for
 *       the given type, or throws
 *       {@link com.equationsolver.exception.UnsupportedEquationTypeException}
 *       if no parser is registered.</li>
 * </ol>
 *
 * <p>Detection priority (first match wins):
 * <pre>
 *   ln(  → LOGARITHMIC
 *   log  → LOGARITHMIC
 *   sin( → SINE
 *   cos( → COSINE
 *   tan( → TANGENT
 *   ^x   → EXPONENTIAL
 *   |    → ABSOLUTE_VALUE
 *   / (  → RATIONAL
 *   x^4  → QUARTIC
 *   x^3  → CUBIC
 *   x^2  → QUADRATIC
 *   else → LINEAR
 * </pre>
 */
public class ParserFactory {

    /**
     * Detects the {@link EquationType} of the given raw equation string.
     *
     * @param input the equation string, e.g. {@code "sin(x) = 0.5"}
     * @return the detected {@link EquationType}; defaults to {@link EquationType#LINEAR}
     *         when no higher-order pattern is found
     */
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

    /**
     * Returns a new {@link EquationParser} instance for the given type.
     *
     * @param type the {@link EquationType} to look up
     * @return a fresh parser that handles {@code type}
     * @throws com.equationsolver.exception.UnsupportedEquationTypeException
     *         if no parser is registered for {@code type}
     */
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