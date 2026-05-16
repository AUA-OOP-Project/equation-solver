package com.equationsolver.parser;

import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;

/**
 * Contract for all equation parsers.
 *
 * <p>A parser is responsible for converting a raw equation string into a typed
 * {@link Equation} model object. Each parser handles exactly one
 * {@link EquationType}; the correct parser is obtained via
 * {@link com.equationsolver.parser.ParserFactory#getParser(EquationType)}.
 *
 * <p>Implementations must throw
 * {@link com.equationsolver.exception.InvalidEquationException} (unchecked) when
 * the input cannot be parsed — never return {@code null} or swallow the error.
 */
public interface EquationParser {

    /**
     * Parses the raw equation string into a typed {@link Equation}.
     *
     * @param input the equation string, e.g. {@code "x^2 - 5x + 6 = 0"}
     * @return the parsed {@link Equation}; never {@code null}
     * @throws com.equationsolver.exception.InvalidEquationException if the
     *         string is malformed or coefficients cannot be extracted
     */
    Equation parse(String input);

    /**
     * Returns {@code true} if this parser handles the given equation type.
     *
     * @param type the {@link EquationType} to check
     * @return {@code true} if this parser supports {@code type}
     */
    boolean supports(EquationType type);
}