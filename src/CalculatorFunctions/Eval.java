package CalculatorFunctions;

public class Eval {

	public static float evalFunction(String equation) {
		float result = 0;
		String function = equation.substring(0, 2);
		int length = 2;

		if (!MathFunctions.isFunction(function)) {
			function = equation.substring(0, 3);
			length = 3;
		}

		if (!MathFunctions.isFunction(function)) {
			function = equation.substring(0, 4);
			length = 4;
		}

		if ("ln".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			if (number == 0) {
				throw new Error("0 in ln is infinity");
			}
			result = (float) Math.log(number);
		} else if ("log".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			if (number == 0) {
				throw new Error("0 in ln is infinity");
			}
			result = (float) Math.log10(number);
		} else if ("sin".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			result = (float) Math.sin(number);
		} else if ("cos".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			result = (float) Math.cos(number);
		} else if ("tan".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			result = (float) Math.tan(number);
		} else if ("sqr".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			result = (float) Math.pow(number, 2);
		} else if ("sqrt".equals(function)) {
			double number = Float.parseFloat(equation.substring(length));
			result = (float) Math.sqrt(number);
		}

		return result;
	}

	public static float parseEquation(String equation) {
		StringBuffer stringNumberOne = new StringBuffer();
		StringBuffer stringNumberTwo = new StringBuffer();
		String operatorString = "";
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

		while (i < equation.length() && MathFunctions.isOperator(equation.substring(i, i + 1))) {
			stringNumberOne.append(equation.charAt(i));
			i++;
		}

		operatorString = equation.substring(i, i + 1);
		i++;

		if (equation.charAt(i) == '-') {
			stringNumberOne.append(equation.charAt(i));
			two = true;
			i++;
		}

		while (i < equation.length() && MathFunctions.isOperator(equation.substring(i, i + 1))) {
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

		if (!MathFunctions.isOperator(operatorString))
			return 0;

		return evalArithmetic(numberOne, operatorString, numberTwo);
	}

	public static float evalArithmetic(float numberOne, String opetatoString, float numberTwo) {
		float result = 0;
		if ("/".equals(opetatoString) && numberTwo == 0)
			throw new ArithmeticException("/ by zero");
		if ("+".equals(opetatoString))
			result = numberOne + numberTwo;
		else if ("-".equals(opetatoString))
			result = numberOne - numberTwo;
		else if ("*".equals(opetatoString))
			result = numberOne * numberTwo;
		else if ("/".equals(opetatoString))
			result = numberOne / numberTwo;
		return result;
	}

}
