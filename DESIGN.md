# Equation Solver

This is a java-based project for solving mathematical equations

## Team Members
- Mari Amirjanyan
- Meri Papyan
- Anna Hovhannisyan

## Planned Features
- Solve linear equations
- Solve quadratic equations
- Parse user input
- Display formatted solutions

---

## Class Diagram

<svg viewBox="0 0 860 560" xmlns="http://www.w3.org/2000/svg" font-family="monospace" font-size="12">
  <rect width="860" height="560" fill="#ffffff"/>

  <!-- EQUATION -->
  <rect x="20" y="30" width="200" height="155" rx="4" fill="#fff" stroke="#2563eb" stroke-width="2"/>
  <rect x="20" y="30" width="200" height="28" rx="4" fill="#2563eb"/>
  <rect x="20" y="50" width="200" height="8" fill="#2563eb"/>
  <text x="120" y="50" text-anchor="middle" fill="white" font-weight="bold" font-size="13">Equation</text>
  <line x1="20" y1="80" x2="220" y2="80" stroke="#2563eb" stroke-width="1"/>
  <text x="30" y="97"  fill="#374151" font-size="11">- rawInput : String</text>
  <text x="30" y="113" fill="#374151" font-size="11">- equationType : String</text>
  <line x1="20" y1="122" x2="220" y2="122" stroke="#d1d5db" stroke-width="1"/>
  <text x="30" y="139" fill="#166534" font-size="11">+ Equation(input : String)</text>
  <text x="30" y="155" fill="#166534" font-size="11">+ getRawInput() : String</text>
  <text x="30" y="171" fill="#166534" font-size="11">+ identifyType() : String</text>

  <!-- PARSER -->
  <rect x="20" y="240" width="200" height="130" rx="4" fill="#fff" stroke="#2563eb" stroke-width="2"/>
  <rect x="20" y="240" width="200" height="28" rx="4" fill="#2563eb"/>
  <rect x="20" y="260" width="200" height="8" fill="#2563eb"/>
  <text x="120" y="260" text-anchor="middle" fill="white" font-weight="bold" font-size="13">Parser</text>
  <line x1="20" y1="290" x2="220" y2="290" stroke="#d1d5db" stroke-width="1"/>
  <text x="30" y="308" fill="#166534" font-size="11">+ parse(input : String)</text>
  <text x="30" y="324" fill="#166534" font-size="11">    : Equation</text>
  <text x="30" y="340" fill="#166534" font-size="11">+ extractCoefficients(eq)</text>
  <text x="30" y="356" fill="#166534" font-size="11">    : double[]</text>

  <!-- EQUATIONSOLVER -->
  <rect x="320" y="30" width="220" height="195" rx="4" fill="#fff" stroke="#7c3aed" stroke-width="2"/>
  <rect x="320" y="30" width="220" height="28" rx="4" fill="#7c3aed"/>
  <rect x="320" y="50" width="220" height="8" fill="#7c3aed"/>
  <text x="430" y="50" text-anchor="middle" fill="white" font-weight="bold" font-size="13">EquationSolver</text>
  <line x1="320" y1="80" x2="540" y2="80" stroke="#d1d5db" stroke-width="1"/>
  <text x="330" y="100" fill="#166534" font-size="11">+ solve(eq : Equation)</text>
  <text x="330" y="116" fill="#166534" font-size="11">    : Solution</text>
  <text x="330" y="133" fill="#166534" font-size="11">+ solveLinear(coeff : double[])</text>
  <text x="330" y="149" fill="#166534" font-size="11">    : Solution</text>
  <text x="330" y="166" fill="#166534" font-size="11">+ solveQuadratic(</text>
  <text x="330" y="182" fill="#166534" font-size="11">    coeff : double[]) : Solution</text>

  <!-- SOLUTION -->
  <rect x="630" y="30" width="210" height="185" rx="4" fill="#fff" stroke="#d97706" stroke-width="2"/>
  <rect x="630" y="30" width="210" height="28" rx="4" fill="#d97706"/>
  <rect x="630" y="50" width="210" height="8" fill="#d97706"/>
  <text x="735" y="50" text-anchor="middle" fill="white" font-weight="bold" font-size="13">Solution</text>
  <line x1="630" y1="80" x2="840" y2="80" stroke="#d97706" stroke-width="1"/>
  <text x="640" y="97"  fill="#374151" font-size="11">- roots : double[]</text>
  <text x="640" y="113" fill="#374151" font-size="11">- equationType : String</text>
  <text x="640" y="129" fill="#374151" font-size="11">- hasSolution : boolean</text>
  <line x1="630" y1="138" x2="840" y2="138" stroke="#d1d5db" stroke-width="1"/>
  <text x="640" y="155" fill="#166534" font-size="11">+ Solution(roots, type)</text>
  <text x="640" y="171" fill="#166534" font-size="11">+ getRoots() : double[]</text>
  <text x="640" y="187" fill="#166534" font-size="11">+ hasSolution() : boolean</text>
  <text x="640" y="203" fill="#166534" font-size="11">+ display() : String</text>

  <!-- USERINTERFACE -->
  <rect x="320" y="360" width="220" height="155" rx="4" fill="#fff" stroke="#db2777" stroke-width="2"/>
  <rect x="320" y="360" width="220" height="28" rx="4" fill="#db2777"/>
  <rect x="320" y="380" width="220" height="8" fill="#db2777"/>
  <text x="430" y="380" text-anchor="middle" fill="white" font-weight="bold" font-size="13">UserInterface</text>
  <line x1="320" y1="410" x2="540" y2="410" stroke="#d1d5db" stroke-width="1"/>
  <text x="330" y="428" fill="#166534" font-size="11">+ start() : void</text>
  <text x="330" y="444" fill="#166534" font-size="11">+ getUserInput() : String</text>
  <text x="330" y="460" fill="#166534" font-size="11">+ displaySolution(s : Solution)</text>
  <text x="330" y="476" fill="#166534" font-size="11">    : void</text>
  <text x="330" y="492" fill="#166534" font-size="11">+ showInstructions() : void</text>

  <!-- ARROWS -->
  <defs>
    <marker id="arr" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
      <polygon points="0 0, 10 3.5, 0 7" fill="#6b7280"/>
    </marker>
  </defs>
  <!-- UserInterface -> Parser -->
  <line x1="320" y1="415" x2="222" y2="320" stroke="#6b7280" stroke-width="1.5" stroke-dasharray="6,3" marker-end="url(#arr)"/>
  <text x="238" y="375" fill="#6b7280" font-size="10" font-style="italic">uses</text>
  <!-- Parser -> Equation -->
  <line x1="120" y1="240" x2="120" y2="186" stroke="#6b7280" stroke-width="1.5" stroke-dasharray="6,3" marker-end="url(#arr)"/>
  <text x="126" y="218" fill="#6b7280" font-size="10" font-style="italic">creates</text>
  <!-- UserInterface -> EquationSolver -->
  <line x1="430" y1="360" x2="430" y2="226" stroke="#6b7280" stroke-width="1.5" stroke-dasharray="6,3" marker-end="url(#arr)"/>
  <text x="436" y="288" fill="#6b7280" font-size="10" font-style="italic">uses</text>
  <!-- EquationSolver -> Equation -->
  <line x1="320" y1="105" x2="222" y2="105" stroke="#6b7280" stroke-width="1.5" stroke-dasharray="6,3" marker-end="url(#arr)"/>
  <text x="232" y="98" fill="#6b7280" font-size="10" font-style="italic">reads</text>
  <!-- EquationSolver -> Solution -->
  <line x1="540" y1="115" x2="628" y2="115" stroke="#6b7280" stroke-width="1.5" stroke-dasharray="6,3" marker-end="url(#arr)"/>
  <text x="549" y="108" fill="#6b7280" font-size="10" font-style="italic">creates</text>
  <!-- UserInterface -> Solution -->
  <line x1="540" y1="395" x2="628" y2="190" stroke="#6b7280" stroke-width="1.5" stroke-dasharray="6,3" marker-end="url(#arr)"/>
  <text x="592" y="315" fill="#6b7280" font-size="10" font-style="italic">displays</text>
</svg>

---

## Project Structure

The project is organized into several main classes, each responsible for a specific part of the program:

### 1. `Equation`
Represents a mathematical equation entered by the user.

Responsibilities:
- Store the raw input
- Identify the type of equation (linear, quadratic)

### 2. `Parser`
Handles converting user input into a structured format.

Responsibilities:
- Read input strings
- Break input into components
- Prepare data for solving

### 3. `EquationSolver`
Contains the core logic for solving equations.

Responsibilities:
- Solve linear equations
- Solve quadratic equations
- Return results in a structured format

### 4. `Solution`
Represents the result of solving an equation.

Responsibilities:
- Store solutions (roots)
- Format output for display

### 5. `UserInterface`
Handles interaction with the user.

Responsibilities:
- Get user input
- Display results
- Show messages and instructions



