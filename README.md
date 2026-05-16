# Equation Solver

A Java application that parses and solves 11 types of mathematical equations, with both a graphical user interface (GUI) and a command-line interface (CLI).

Built with Java 17, Maven, and Swing + FlatLaf for the GUI.

## Team

- Mari Amirjanyan
- Meri Papyan
- Anna Hovhannisyan

---

## Supported Equation Types

| Type | Example |
|---|---|
| Linear | `2x + 4 = 0` |
| Quadratic | `x^2 - 5x + 6 = 0` |
| Cubic | `x^3 - 6x^2 + 11x - 6 = 0` |
| Quartic | `x^4 - 5x^2 + 4 = 0` |
| Exponential | `2^x = 8` |
| Logarithmic | `log2(x) = 3` or `ln(x) = 1` |
| Rational | `(x^2 - 1) / (x - 2) = 0` |
| Absolute Value | `\|2x + 3\| = 7` |
| Sine | `sin(x) = 0.5` |
| Cosine | `cos(x) = 1` |
| Tangent | `tan(x) = 2` |

---

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

---

## Running the Application

### GUI (Graphical Interface)

```bash
mvn compile exec:java -Dexec.mainClass="com.equationsolver.app.EquationSolverGUI"
```

The GUI features:
- Equation type detection as you type
- Step-by-step solution display
- Equation type list with examples (click to load)
- Solve history panel
- Copy result to clipboard

### CLI (Command Line)

```bash
mvn compile exec:java -Dexec.mainClass="com.equationsolver.app.EquationApp"
```

---

## Project Structure

```
src/main/java/com/equationsolver/
├── app/
│   ├── EquationApp.java          # CLI entry point
│   └── EquationSolverGUI.java    # GUI entry point
├── model/
│   ├── Equation.java             # Abstract base for all equations
│   ├── PolynomialEquation.java   # Base for linear/quadratic/cubic/quartic
│   ├── TranscendentalEquation.java
│   ├── TrigonometricEquation.java
│   ├── Solution.java             # Immutable result value object
│   ├── EquationType.java         # Enum of all supported types
│   └── ...                       # One model class per equation type
├── parser/
│   ├── EquationParser.java       # Parser interface
│   ├── ParserFactory.java        # Auto-detects type and returns parser
│   └── ...                       # One parser per equation type
├── solver/
│   ├── EquationSolver.java       # Abstract solver (Template Method pattern)
│   ├── SolverFactory.java        # Returns the correct solver for a type
│   └── ...                       # One solver per equation type
└── exception/
    ├── InvalidEquationException.java
    └── UnsupportedEquationTypeException.java
```

---

## Architecture

The application uses two design patterns:

**Template Method** — `EquationSolver.solve()` is `final` and calls the abstract `validate()` and `doSolve()` methods, enforcing a consistent pipeline across all solvers.

**Factory** — `ParserFactory` detects the equation type from the input string and returns the correct parser. `SolverFactory` returns the correct solver for a given type.

---

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| FlatLaf | 3.4.1 | Modern dark theme for Swing GUI |
