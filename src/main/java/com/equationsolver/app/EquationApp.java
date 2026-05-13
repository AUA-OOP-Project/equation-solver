package com.equationsolver.app;

import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;
import com.equationsolver.parser.EquationParser;
import com.equationsolver.parser.ParserFactory;
import com.equationsolver.solver.EquationSolver;
import com.equationsolver.solver.SolverFactory;

import java.util.Scanner;

public class EquationApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("================================================");
        System.out.println("              Equation Solver                   ");
        System.out.println("================================================");
        System.out.println("Supported equation types:");
        System.out.println("  Linear          2x + 4 = 0");
        System.out.println("  Quadratic       x^2 - 5x + 6 = 0");
        System.out.println("  Cubic           x^3 - 6x^2 + 11x - 6 = 0");
        System.out.println("  Quartic         x^4 - 5x^2 + 4 = 0");
        System.out.println("  Exponential     2^x = 8");
        System.out.println("  Logarithmic     log2(x) = 3  |  log(x) = 2  |  ln(x) = 1");
        System.out.println("  Rational        (x^2 - 1) / (x - 2) = 0");
        System.out.println("  Absolute Value  |2x + 3| = 7");
        System.out.println("  Sine            sin(x) = 0.5");
        System.out.println("  Cosine          cos(x) = 1");
        System.out.println("  Tangent         tan(x) = 2");
        System.out.println("------------------------------------------------");
        System.out.println("Type 'exit' to quit");

        while (true) {
            System.out.print("\nEnter equation: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            if (input.isEmpty()) continue;

            try {
                EquationType type     = ParserFactory.detectType(input);
                EquationParser parser = ParserFactory.getParser(type);
                Equation equation     = parser.parse(input);
                EquationSolver solver = SolverFactory.getSolver(type);
                Solution solution     = solver.solve(equation);

                System.out.println("\n" + solution.displayWithSteps());

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}