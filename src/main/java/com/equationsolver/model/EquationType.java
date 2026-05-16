package com.equationsolver.model;

/**
 * Enumeration of all equation types supported by the solver.
 *
 * <p>Used by {@link com.equationsolver.parser.ParserFactory} to select the
 * correct parser and by {@link com.equationsolver.solver.SolverFactory} to
 * select the correct solver. Solvers validate the incoming {@link Equation}
 * against this type rather than using {@code instanceof} checks.
 */
public enum EquationType {
    /** ax + b = 0 */
    LINEAR,
    /** ax² + bx + c = 0 */
    QUADRATIC,
    /** ax³ + bx² + cx + d = 0 */
    CUBIC,
    /** ax⁴ + bx³ + cx² + dx + e = 0 */
    QUARTIC,
    /** Polynomial of degree ≥ 5 (not yet fully supported). */
    HIGHER_DEGREE,
    /** a^x = b */
    EXPONENTIAL,
    /** log_a(x) = b  or  ln(x) = b */
    LOGARITHMIC,
    /** sin(x) = rhs */
    SINE,
    /** cos(x) = rhs */
    COSINE,
    /** tan(x) = rhs */
    TANGENT,
    /** P(x) / Q(x) = 0 */
    RATIONAL,
    /** |ax + b| = c */
    ABSOLUTE_VALUE
}