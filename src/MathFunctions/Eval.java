package MathFunctions;

public class Eval {

    String[] stack;

    public static float eval(String num1, String opetatoString, String num2) {
        float result = 0;
        final float numberOne = Float.parseFloat(num1);
        final float numberTwo = Float.parseFloat(num2);
        switch (opetatoString) {
        case "+":
            result = numberOne + numberTwo;
            break;
        case "-":
            result = numberOne - numberTwo;
            break;
        case "*":
            result = numberOne * numberTwo;
            break;
        case "/":
            result = numberOne / numberTwo;
            break;
        case "^":
            result = (float) Math.pow(numberOne, numberTwo);
            break;

        default:
            result = 0;
        }
        return result;
    }

}
