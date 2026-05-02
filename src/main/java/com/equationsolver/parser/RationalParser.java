public Equation parse(String input) {
    String raw = input.trim();

    // check if it's rational before anything else
    if (raw.contains("/")) {
        return parseRational(raw);
    }

    String normalized = normalize(raw);
    int degree = detectDegree(normalized);
    double[] coefficients = extractCoefficients(normalized, degree);

    return switch (degree) {
        case 2 -> new QuadraticEquation(raw, coefficients[0], coefficients[1], coefficients[2]);
        case 3 -> new CubicEquation(raw, coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
        case 4 -> new QuarticEquation(raw, coefficients[0], coefficients[1], coefficients[2], coefficients[3], coefficients[4]);
        default -> throw new IllegalArgumentException("Unsupported degree: " + degree);
    };
}

private Equation parseRational(String raw) {
    // strip '= 0' and split on '/'
    String lhs = raw.trim();
    if (lhs.toLowerCase().endsWith("=0")) {
        lhs = lhs.substring(0, lhs.lastIndexOf("=")).trim();
    }

    // strip outer parentheses from numerator and denominator
    // expected format: (numerator) / (denominator)
    Pattern p = Pattern.compile("\\(([^)]+)\\)\\s*/\\s*\\(([^)]+)\\)");
    Matcher m = p.matcher(lhs);

    if (!m.find()) {
        throw new IllegalArgumentException("Rational equation must be in the form (numerator) / (denominator) = 0");
    }

    String numStr = m.group(1).trim();
    String denStr = m.group(2).trim();

    int numDegree = detectDegree(normalize(numStr));
    int denDegree = detectDegree(normalize(denStr));

    double[] numerator   = extractCoefficients(normalize(numStr), numDegree);
    double[] denominator = extractCoefficients(normalize(denStr), denDegree);

    return new RationalEquation(raw, numerator, denominator);
}