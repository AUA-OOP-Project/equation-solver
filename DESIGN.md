# Equation solver 

This is a java-based project for solving mathematical equations

# Team Members
- Mari Amirjanyan
- Meri Papyan
- Anna Hovhannisyan

# Planned features 
- Solve linear equations
- Solve quadratic equations
- Parse user input
- Display formatted solutions

## Project Structure

The project is organized into several main classes, each responsible for a specific part of the program:

1. Equation
Represents a mathematical equation entered by the user.

Responsibilities:
- Store the raw input
- Identify the type of equation (linear, quadratic)

 
2. Parser
Handles converting user input into a structured format.

Responsibilities:
- Read input strings
- Break input into components
- Prepare data for solving


3. EquationSolver
Contains the core logic for solving equations.

Responsibilities:
- Solve linear equations
- Solve quadratic equations
- Return results in a structured format


4. Solution
Represents the result of solving an equation.

Responsibilities:
- Store solutions (roots)
- Format output for display


5. UserInterface
Handles interaction with the user.

Responsibilities:
- Get user input
- Display results
- Show messages and instructions



