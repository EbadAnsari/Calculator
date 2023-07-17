package CalculatorUI;

import java.util.Stack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import CalculatorFunctions.Eval;
import CalculatorFunctions.MathFunctions;

public class Calculator implements ActionListener {

	private JFrame frame;

	/**
	 * It is used to store current latest expression, including all operators.
	 */
	private JLabel inputNumber;

	/**
	 * It is used to store previous expression, except all trigo. functions.
	 */
	private JLabel resultNumberInput;
	private JPanel inputPanel;
	private JPanel buttonsPanel;

	private StringBuilder numberOne = new StringBuilder();
	private StringBuilder numberTwo = new StringBuilder();
	private String operator = "";
	private String function = "";
	private String lastDigit = "";

	private final Color defaultBackgroundColor = new Color(238, 238, 238);
	private final Color buttonColorGrey = new Color(240, 240, 240);
	private final Color buttonColorLightBlue = new Color(223, 244, 255);
	private final Color buttonColorDarkBlue = new Color(0, 99, 153);
	private final Color buttonColorLightPink = new Color(255, 220, 240);
	private final Color evaluateButtonColorGreen = new Color(54, 233, 114);

	private final Color errorTextColorRed = new Color(255, 0, 0);
	private final Color errorBackgroundColorRed = new Color(255, 217, 217);

	private final String[][] buttonText = {

			{ "+", "-", "/", "*", "BS" },

			{ "7", "8", "9", "sin", "C" },

			{ "4", "5", "6", "cos", "log" },

			{ "1", "2", "3", "tan", "ln" },

			{ "0", ".", "=", "sqrt", "sqr" }

	};

	private void updateResultNumberInput() {
		if (operator.length() != 0)
			resultNumberInput.setText(numberOne + operator);
		else {
			resultNumberInput.setText("");
		}
	}

	private void updateNumberInput() {
		if (operator.length() == 0)
			inputNumber.setText(numberOne.toString());
		else
			inputNumber.setText(numberTwo.toString());
	}

	private void backSpace() {
		if (resultNumberInput.getText().length() != 0) {
			if ((numberTwo.length() > 0 && numberTwo.charAt(0) == '-'
					&& MathFunctions.isFunction(numberTwo.substring(1)))
					|| MathFunctions.isFunction(numberTwo.substring(0))) {
				numberTwo = new StringBuilder();
				function = "";
			} else if (numberTwo.length() != 0) {
				numberTwo.deleteCharAt(numberTwo.length() - 1);
			}
		} else {
			if ((numberOne.charAt(0) == '-' && MathFunctions.isFunction(numberOne.substring(1)))
					|| MathFunctions.isFunction(numberOne.substring(0))) {
				numberOne = new StringBuilder();
				function = "";
			} else if (numberOne.length() != 0) {
				numberOne.deleteCharAt(numberOne.length() - 1);
			}
		}
		// if (numberTwo.length() != 0 || operator.length() != 0) {
		// }

		// if (function.length() == 0) {
		// return;
		// }

		// if (operator.length() == 0) {
		// numberOne.deleteCharAt(numberOne.length() - 1);
		// } else if (operator.length() != 0) {
		// numberTwo.deleteCharAt(numberTwo.length() - 1);
		// }
	}

	private void clearButton() {
		numberOne = new StringBuilder();
		numberTwo = new StringBuilder();
		function = "";
		operator = "";
		lastDigit = "";

		inputNumber.setText("");
		resultNumberInput.setText("");
	}

	private void evaluate() {
		if (MathFunctions.isFunction(function)) {
			boolean first = false;
			boolean second = false;
			try {
				boolean sign = false;
				if (MathFunctions.containFunction(numberOne.toString())) {
					if (numberOne.charAt(0) == '-') {
						sign = true;
						numberOne.deleteCharAt(0);
					}
					numberOne = new StringBuilder((sign ? "-" : "") + Eval.evalFunction(numberOne.toString()));
					first = true;
				} else if (MathFunctions.containFunction(numberTwo.toString())) {
					if (numberTwo.charAt(0) == '-') {
						sign = true;
						numberTwo.deleteCharAt(0);
					}
					numberTwo = new StringBuilder((sign ? "-" : "") + Eval.evalFunction(numberTwo.toString()));
					second = true;
				}
			} catch (Error error) {
				inputNumber.setForeground(errorTextColorRed);
				inputPanel.setBackground(errorBackgroundColorRed);
				resultNumberInput.setForeground(errorTextColorRed);
				inputNumber.setText("");
				numberTwo = new StringBuilder();
				return;
			}

			if (first && MathFunctions.isInt(numberOne.toString())) {
				numberOne = new StringBuilder(numberOne.toString().split("[.]")[0]);
			} else if (second && MathFunctions.isInt(numberTwo.toString())) {
				numberTwo = new StringBuilder(numberTwo.toString().split("[.]")[0]);
			}

			function = "";
			// return;
		}

		if (operator.length() != 0 && !numberOne.isEmpty() && !numberTwo.isEmpty()
				&& MathFunctions.isFloat(numberOne.toString()) && MathFunctions.isFloat(numberTwo.toString())) {
			final float numOne = Float.parseFloat(numberOne.toString());
			final float numTwo = Float.parseFloat(numberTwo.toString());

			try {
				numberOne = new StringBuilder("" + Eval.evalArithmetic(numOne, operator, numTwo));
			} catch (ArithmeticException error) {
				System.out.println(error.getMessage());
				inputNumber.setForeground(errorTextColorRed);
				inputPanel.setBackground(errorBackgroundColorRed);
				resultNumberInput.setForeground(errorTextColorRed);
				inputNumber.setText("");
				numberTwo = new StringBuilder();
				return;
			}

			if (MathFunctions.isInt(numberOne.toString())) {
				numberOne = new StringBuilder(numberOne.toString().split("[.]")[0]);
			}

			numberTwo = new StringBuilder("");
			operator = "";
		}
	}

	private void getInput() {

		inputNumber.setForeground(Color.BLACK);
		inputPanel.setBackground(defaultBackgroundColor);
		resultNumberInput.setForeground(Color.BLACK);

		if ("BS".equals(lastDigit)) {
			backSpace();
		} else if ("C".equals(lastDigit)) {
			clearButton();
		} else if ("=".equals(lastDigit)) {
			evaluate();
		} else {
			if (MathFunctions.isFunction(lastDigit)) {

				if (operator.length() == 0) {
					return;
				}

				if (MathFunctions.isFunction(numberOne.toString()) || (numberOne.length() > 2
						&& MathFunctions.isFunction(numberOne.substring(1)) && numberOne.substring(0, 1).equals("-"))) {
					numberOne = new StringBuilder(lastDigit);
				} else if (MathFunctions.isFunction(numberTwo.toString()) || (numberTwo.length() > 2
						&& MathFunctions.isFunction(numberTwo.substring(1)) && numberTwo.substring(0, 1).equals("-"))) {
					numberTwo = new StringBuilder(lastDigit);
				} else if (numberTwo.length() == 0 && !MathFunctions.isFloat(numberOne.toString())) {
					numberOne.append(lastDigit);
				} else if (numberTwo.length() == 0 && MathFunctions.isFloat(numberOne.toString())) {
					numberTwo.append(lastDigit);
				} else if (numberTwo.length() == 0 || "-".equals(numberTwo.toString())) {
					evaluate();
					numberTwo.append(lastDigit);
				}

				function = lastDigit;
				// if (MathFunctions.isFunction(function)) {
				// evaluate();
				// numberTwo.append(lastDigit);
				// } else if (MathFunctions.isFunction(numberOne.toString())) {
				// if (!"-".equals(numberOne.toString())) {
				// numberOne = new StringBuilder();
				// }
				// numberOne.append(lastDigit);
				// } else if (MathFunctions.isFunction(numberTwo.toString())) {
				// if (!"-".equals(numberTwo.toString())) {
				// numberTwo = new StringBuilder();
				// }
				// numberTwo.append(lastDigit);
				// } else if (numberOne.length() == 0) {
				// numberOne.append(lastDigit);
				// function = lastDigit;
				// } else if (numberTwo.length() == 0) {
				// numberTwo.append(lastDigit);
				// function = lastDigit;
				// } else if (numberTwo.length() != 0
				// && (MathFunctions.isFunction(numberTwo.toString()) ||
				// "-".equals(numberTwo.toString()))) {
				// // if (numberTwo.toString().contains("-")) {
				// numberTwo = new StringBuilder(numberTwo.toString().contains("-") ? "-" : "");
				// // }
				// numberTwo.append(lastDigit);
				// function = lastDigit;
				// } else if (numberTwo.length() != 0) {
				// evaluate();
				// operator = "*";
				// numberTwo = new StringBuilder();
				// numberTwo.append(lastDigit);
				// function = lastDigit;
				// }

				// if (numberTwo.length() == 0) {
				// inputNumber.setText(numberOne.toString() + operator);
				// } else {
				// inputNumber.setText(numberTwo.toString());
				// resultNumberInput.setText(numberOne.toString());
				// }

				System.out.println("numberOne = " + numberOne);
				System.out.println("numberTwo = " + numberTwo);
				System.out.println("operator  = " + operator);
				System.out.println();
				// return;
			} else if (MathFunctions.isOperator(lastDigit)) {
				if (MathFunctions.isFunction(function)) {
					if (MathFunctions.containFunction(numberOne.toString())) {
						evaluate();
						operator = lastDigit;
					} else if (MathFunctions.containFunction(numberTwo.toString())) {
						evaluate();
						operator = lastDigit;
					}
				} else if (operator.length() == 0 && numberOne.length() == 0 && "-".equals(lastDigit)) {
					numberOne.append(lastDigit);
				} else if (operator.length() != 0 && numberTwo.length() == 0 && "-".equals(lastDigit)) {
					numberTwo.append(lastDigit);
				} else {
					evaluate();
					operator = lastDigit;
				}
			} else if ((operator.length() == 0) || (function.length() != 0 && numberOne.length() == 0)) {
				if (MathFunctions.isDot(lastDigit) && numberOne.indexOf(".") == -1) {
					numberOne.append(lastDigit);
				} else if (!MathFunctions.isDot(lastDigit)) {
					numberOne.append(lastDigit);
				}
			} else if (operator.length() != 0) {
				if (MathFunctions.isDot(lastDigit) && numberTwo.indexOf(".") == -1) {
					numberTwo.append(lastDigit);
				} else if (!MathFunctions.isDot(lastDigit)) {
					numberTwo.append(lastDigit);
				}
			}

		}

		System.out.println("numberOne = " + numberOne);
		System.out.println("numberTwo = " + numberTwo);
		System.out.println("operator  = " + operator);
		System.out.println();

		updateNumberInput();
		updateResultNumberInput();

	}

	private void initFrame() {
		final int ratioW = 185;
		final int ratioH = 226;
		final double expandRatio = 2.5;

		frame = new JFrame("Calculator in Java");
		frame.setSize((int) (ratioW * expandRatio), (int) (ratioH * expandRatio));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(17, 10));
		frame.setVisible(true);
		frame.setBackground(buttonColorGrey);
	}

	private void initButtonPanel() {
		buttonsPanel = new JPanel(new GridLayout(5, 5));
		buttonsPanel.setBackground(buttonColorGrey);
	}

	private void initInputPanel() {
		inputPanel = new JPanel(new BorderLayout());
		inputPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() * 25 / 100));
		inputPanel.add(inputNumber, BorderLayout.CENTER);
		inputPanel.add(resultNumberInput, BorderLayout.BEFORE_FIRST_LINE);
	}

	private void initInputText() {
		inputNumber = new JLabel("");
		inputNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		inputNumber.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 15));
		Font font = inputNumber.getFont();
		float fontSize = font.getSize() + 20;
		inputNumber.setFont(font.deriveFont(fontSize));
	}

	private void initBufferNumberInput() {
		resultNumberInput = new JLabel("");
		resultNumberInput.setHorizontalAlignment(SwingConstants.RIGHT);
		resultNumberInput.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 15));
		Font font = resultNumberInput.getFont();
		float fontSize = font.getSize() + 3;
		resultNumberInput.setFont(font.deriveFont(fontSize));
	}

	private JButton createButton(String buttonText, Color buttonColor) {
		final JButton button = new JButton(buttonText, null);
		final Font font = button.getFont();
		float fontSize = font.getSize() + 5;
		button.setFont(font.deriveFont(fontSize));

		button.setBorder(null);
		button.setBackground(buttonColor);
		button.setFocusPainted(false);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(buttonColorLightBlue);
				button.setForeground(buttonColorDarkBlue);
				// System.out.println(buttonText);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				lastDigit = buttonText;
				getInput();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(buttonColor);
				button.setForeground(Color.BLACK);
			}
		});
		return button;
	}

	public Calculator() {

		initFrame();
		initButtonPanel();
		initInputText();
		initBufferNumberInput();
		initInputPanel();

		for (int i = 1; i <= buttonText.length; i++) {
			for (int j = 1; j <= buttonText[i - 1].length; j++) {
				final String btnTxt = buttonText[i - 1][j - 1];

				if ("=".equals(btnTxt)) {
					buttonsPanel.add(createButton(btnTxt, evaluateButtonColorGreen));
				} else if (j == buttonText[i - 1].length) {
					buttonsPanel.add(createButton(btnTxt, buttonColorLightPink));
				} else {
					buttonsPanel.add(createButton(btnTxt, buttonColorGrey));
				}
			}
		}

		frame.add(inputPanel, BorderLayout.NORTH);
		frame.add(buttonsPanel);

		frame.setVisible(true);
	}

	public double getResult() {
		return Float.parseFloat(resultNumberInput.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
