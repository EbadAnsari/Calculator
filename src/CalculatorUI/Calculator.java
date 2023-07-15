package CalculatorUI;

import java.util.Stack;
import java.util.Vector;
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

import MathFunctions.Eval;

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

	private Stack<String> inputNumberStack = new Stack<>();
	private Stack<String> resultNumberStack = new Stack<>();
	private String lastDigit = "";
	private boolean isOperatorIn = false;

	private final Color defaultBackgroundColor = new Color(238, 238, 238);
	private final Color buttonColorGrey = new Color(240, 240, 240);
	private final Color buttonColorLightBlue = new Color(223, 244, 255);
	private final Color buttonColorDarkBlue = new Color(0, 99, 153);
	private final Color buttonColorLightPink = new Color(255, 220, 240);
	private final Color evaluateButtonColorGreen = new Color(54, 233, 114);

	private final Color errorTextColorRed = new Color(255, 0, 0);
	private final Color errorBackgroundColorRed = new Color(255, 217, 217);

	private final String[][] buttonText = { { "+", "-", "/", "*", "C" }, { "7", "8", "9", "sin", "log" },
			{ "4", "5", "6", "cos", "pi" }, { "1", "2", "3", "tan", "e" }, { "0", ".", "=", "sqrt", "sqr" } };

	private boolean isInt(String number) {
		String[] splitResult = number.split("[.]");
		if (splitResult.length <= 1)
			return true;

		if ("0".equals(splitResult[1]))
			return true;

		return false;
	}

	private void updateResultNumberInput() {
		// resultNumberInput.setText(stackToString(resultNumberStack));
		// if (operator.length() != 0)
		resultNumberInput.setText(numberOne + operator);
	}

	private void updateNumberInput() {
		if (operator.length() == 0)
			inputNumber.setText(numberOne.toString());
		else
			inputNumber.setText(numberTwo.toString());
		// inputNumber.setText(stackToString(inputNumberStack));
	}

	private String stackToString(Stack<String> stk) {
		return stk.toString().substring(1, stk.toString().length() - 1).replaceAll(", ", "");
	}

	private void cloneStack(Stack<String> srcStk, Stack<String> destStk) {
		if (srcStk.empty())
			return;
		String string = srcStk.pop();
		cloneStack(srcStk, destStk);
		srcStk.push(string);
		destStk.push(string);
	}

	private String emptyStack(Stack<String> stk) {
		if (stk.empty())
			return "";
		String last = stk.pop();
		return emptyStack(stk) + last;
	}

	private boolean isDot() {
		return ".".equals(lastDigit);
	}

	private boolean isNumber() {
		return (lastDigit.length() == 1 && 48 <= lastDigit.charAt(0) && lastDigit.charAt(0) <= 57);
	}

	private boolean isFunction() {
		return ("log".equals(lastDigit) || "sin".equals(lastDigit) || "cos".equals(lastDigit) || "pi".equals(lastDigit)
				|| "tan".equals(lastDigit) || "e".equals(lastDigit) || "sqrt".equals(lastDigit)
				|| "sqr".equals(lastDigit));
	}

	private boolean isOperator(String operator) {
		return ("+".equals(operator) || "-".equals(operator) || "/".equals(operator) || "*".equals(operator));
	}

	private void evaluate() {
		if (operator.length() != 0 && !numberOne.isEmpty() && !numberTwo.isEmpty()) {
			final float numOne = Float.parseFloat(numberOne.toString());
			final float numTwo = Float.parseFloat(numberTwo.toString());

			// numberOne = new StringBuilder();

			try {
				numberOne = new StringBuilder("" + Eval.evalArithmetic(numOne, operator.charAt(0), numTwo));
			} catch (ArithmeticException error) {
				System.out.println(error.getMessage());
				inputNumber.setForeground(errorTextColorRed);
				inputPanel.setBackground(errorBackgroundColorRed);
				resultNumberInput.setForeground(errorTextColorRed);
				return;
			}

			if (isInt(numberOne.toString())) {
				numberOne = new StringBuilder(numberOne.toString().split("[.]")[0]);
			}

			numberTwo = new StringBuilder("");
			operator = "";
		}
	}

	private void clearInput() {
		emptyStack(inputNumberStack);
		emptyStack(resultNumberStack);
		lastDigit = "";
		updateNumberInput();
		updateResultNumberInput();
	}

	private void getInput() {

		inputNumber.setForeground(Color.BLACK);
		inputPanel.setBackground(defaultBackgroundColor);
		resultNumberInput.setForeground(Color.BLACK);

		if ("C".equals(lastDigit)) {
			backSpace();
		} else if ("=".equals(lastDigit)) {
			evaluate();
		} else {
			if (isOperator(lastDigit)) {
				evaluate();
			}

			if (isOperator(lastDigit)) {
				operator = lastDigit;
			} else if (operator.length() == 0) {
				if (isDot() && numberOne.indexOf(".") == -1) {
					numberOne.append(lastDigit);
				} else if (!isDot()) {
					numberOne.append(lastDigit);
				}
			} else if (operator.length() != 0) {
				numberTwo.append(lastDigit);
			}

		}

		updateNumberInput();
		updateResultNumberInput();

	}

	private void backSpace() {
		if (inputNumberStack.empty())
			return;
		inputNumberStack.pop();
		inputNumber.setText(stackToString(inputNumberStack));
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
		inputNumber = new JLabel(!inputNumberStack.empty() ? inputNumberStack.peek() : "");
		inputNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		inputNumber.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 15));
		Font font = inputNumber.getFont();
		float fontSize = font.getSize() + 20;
		inputNumber.setFont(font.deriveFont(fontSize));
	}

	private void initBufferNumberInput() {
		resultNumberInput = new JLabel(!inputNumberStack.empty() ? inputNumberStack.peek() : "");
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
				// if ("C".equals(buttonText)) {
				// backSpace();
				// } else if ("=".equals(lastDigit)) {
				// System.out.println(buttonText);
				// evaluate();
				// } else {
				getInput();
				// }
				// System.out.print(stackToString(resultNumberStack) + " ");
				// System.out.print(stackToString(inputNumberStack));
				// System.out.println();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(buttonColor);
				button.setForeground(Color.BLACK);
				// System.out.println(buttonText);
			}
		});
		return button;
	}

	public Calculator() {

		// inputNumberStack.push("");

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

		// for (int i = 1; i <= 25; i++) {
		// }

		frame.add(inputPanel, BorderLayout.NORTH);
		frame.add(buttonsPanel);

		frame.setVisible(true);
	}

	public double getResult() {
		return 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
