package MathFunctions;

public class Eval {

	String[] stack;

	private static boolean isOperator(char operator) {
		return (operator == '+' || operator == '-' || operator == '/' || operator == '*');
	}

	public static float parseEquation(String equation) {
		StringBuffer stringNumberOne = new StringBuffer();
		StringBuffer stringNumberTwo = new StringBuffer();
		char operatorString = 'n';
		boolean one = false;
		boolean two = false;

		float numberOne = 0;
		float numberTwo = 0;

		int i = 0;
		if (equation.charAt(0) == '-') {
			stringNumberOne.append(equation.charAt(i));
			one = true;
			i++;
		}

		while (i < equation.length() && isOperator(equation.charAt(i))) {
			stringNumberOne.append(equation.charAt(i));
			i++;
		}

		operatorString = equation.charAt(i);
		i++;

		if (equation.charAt(i) == '-') {
			stringNumberOne.append(equation.charAt(i));
			two = true;
			i++;
		}

		while (i < equation.length() && isOperator(equation.charAt(i))) {
			stringNumberOne.append(equation.charAt(i));
			i++;
		}

		if (one && stringNumberOne.length() > 1) {
			numberOne = Float.parseFloat(stringNumberOne.toString());
		} else if (stringNumberOne.length() > 0) {
			numberOne = Float.parseFloat(stringNumberOne.toString());
		} else
			return 0;

		if (two && stringNumberTwo.length() > 1) {
			numberTwo = Float.parseFloat(stringNumberTwo.toString());
		} else if (stringNumberTwo.length() > 0) {
			numberTwo = Float.parseFloat(stringNumberTwo.toString());
		} else
			return 0;

		if (!isOperator(operatorString))
			return 0;

		return evalArithmetic(numberOne, operatorString, numberTwo);
	}

	public static float evalArithmetic(float numberOne, char opetatoString, float numberTwo) {
		float result = 0;

		if (opetatoString == '/' && numberTwo == 0) {
			throw new ArithmeticException("/ by zero");
		}

		switch (opetatoString) {
		case '+':
			result = numberOne + numberTwo;
			break;
		case '-':
			result = numberOne - numberTwo;
			break;
		case '*':
			result = numberOne * numberTwo;
			break;
		case '/':
			result = numberOne / numberTwo;
			break;

		default:
			result = 0;
		}
		return result;
	}

}
