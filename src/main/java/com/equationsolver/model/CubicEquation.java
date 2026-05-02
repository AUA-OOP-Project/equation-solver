public class CubicEquation extends PolynomialEquation {
    private double a;  // coefficient of x³
    private double b;  // coefficient of x²
    private double c;  // coefficient of x
    private double d;  // constant term


    ublic CubicEquation(String rawInput, double a, double b, double c, double d) {
        super(rawInput, EquationType.CUBIC, a, b, c, d);
    }
}