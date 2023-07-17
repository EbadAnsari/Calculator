package CalculatorFunctions;

public class MathFunctions {
    public static boolean isFloat(String numberString) {
        try {
            Float.parseFloat(numberString);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }

    public static boolean isInt(String number) {
        String[] splitResult = number.split("[.]");
        return splitResult.length <= 1 || "0".equals(splitResult[1]);
    }

    public static boolean containFunction(String functionEquation) {
        String[] functionsArray = { "ln", "sin", "cos", "tan", "log", "sqr", "sqrt" };

        for (String string : functionsArray) {
            if (functionEquation.contains(string))
                return true;
        }

        return false;
    }

    public static boolean isDot(String dot) {
        return ".".equals(dot);
    }

    public static boolean isFunction(String function) {
        return ("log".equals(function) || "sin".equals(function) || "cos".equals(function) || "tan".equals(function)
                || "ln".equals(function) || "sqrt".equals(function) || "sqr".equals(function));
    }

    public static boolean isOperator(String operator) {
        return ("+".equals(operator) || "-".equals(operator) || "/".equals(operator) || "*".equals(operator));
    }
}
