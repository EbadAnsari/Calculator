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

public class Calculator implements ActionListener {

	private JFrame frame;
	private JLabel numberInput;
	private JLabel resultNumberInput;
	private JPanel inputPanel;
	private JPanel buttonsPanel;

	private Stack<String> lastInputDigit = new Stack<>();

	// private String[] lastInputDigit = {};

	private final Color buttonColorGrey = new Color(242, 242, 242);
	private final Color buttonColorLightBlue = new Color(223, 244, 255);
	private final Color buttonColorDarkBlue = new Color(0, 99, 153);
	private final Color buttonColorLightPink = new Color(255, 220, 240);

	private final String[] buttonText = { "+", "-", "/", "*", "<", "7", "8", "9", "sin", "log", "4", "5", "6", "cos",
			"pi", "1", "2", "3", "tan", "e", "0", ".", "=", "sqrt", "sqr" };

	private String stackToString() {
		return lastInputDigit.toString().substring(1, lastInputDigit.toString().length() - 1).replaceAll(", ", "");
	}

	private boolean isDot() {
		return ".".equals(lastInputDigit.peek());
	}

	private boolean isNumber() {
		return (lastInputDigit.peek().length() == 1 && 48 <= lastInputDigit.peek().charAt(0)
				&& lastInputDigit.peek().charAt(0) <= 57);
	}

	private boolean isFunction() {
		return ("log".equals(lastInputDigit.peek()) || "sin".equals(lastInputDigit.peek())
				|| "cos".equals(lastInputDigit.peek()) || "pi".equals(lastInputDigit.peek())
				|| "tan".equals(lastInputDigit.peek()) || "e".equals(lastInputDigit.peek())
				|| "sqrt".equals(lastInputDigit.peek()) || "sqr".equals(lastInputDigit.peek()));
	}

	private boolean isOperator() {
		return ("+".equals(lastInputDigit.peek()) || "-".equals(lastInputDigit.peek())
				|| "/".equals(lastInputDigit.peek()) || "*".equals(lastInputDigit.peek()));
	}

	private void getInput() {
		numberInput.setText(numberInput.getText() + lastInputDigit.peek());
	}

	private void backSpace() {
		if (lastInputDigit.empty())
			return;
		lastInputDigit.pop();
		numberInput.setText(stackToString());
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

	private void initBufferNumberInput() {
		resultNumberInput = new JLabel(lastInputDigit.peek());
		resultNumberInput.setHorizontalAlignment(SwingConstants.RIGHT);
		resultNumberInput.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 15));
	}

	private void initButtonPanel() {
		buttonsPanel = new JPanel(new GridLayout(5, 5));
		buttonsPanel.setBackground(buttonColorGrey);
	}

	private void initInputPanel() {
		inputPanel = new JPanel(new BorderLayout());
		inputPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() * 25 / 100));
		inputPanel.add(numberInput, BorderLayout.CENTER);
		inputPanel.add(resultNumberInput, BorderLayout.BEFORE_FIRST_LINE);
	}

	private void initInputText() {
		numberInput = new JLabel(lastInputDigit.peek());
		numberInput.setHorizontalAlignment(SwingConstants.RIGHT);
		Font font = numberInput.getFont();
		float fontSize = font.getSize() + 10;
		numberInput.setFont(font.deriveFont(fontSize));
		numberInput.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 15));
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
				if ("<".equals(buttonText)) {
					backSpace();
				} else {
					lastInputDigit.push(buttonText);
					getInput();
				}
				System.out.println(stackToString());
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

		lastInputDigit.push("Hello");

		initFrame();
		initButtonPanel();
		initInputText();
		initBufferNumberInput();
		initInputPanel();

		for (int i = 1; i <= 25; i++) {
			if ((i % 5) == 0)
				buttonsPanel.add(createButton(buttonText[i - 1], buttonColorLightPink));
			else
				buttonsPanel.add(createButton(buttonText[i - 1], buttonColorGrey));
		}

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
