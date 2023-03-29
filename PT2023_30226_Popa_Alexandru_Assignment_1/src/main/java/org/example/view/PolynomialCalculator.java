package org.example.view;

import org.example.model.Polynomial;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PolynomialCalculator extends JFrame implements ActionListener {

    private final JTextField input1Field;
    private final JTextField input2Field;
    private final JLabel resultLabel;

    private static final int FADE_DURATION_MS = 1000; // Fade-in duration in milliseconds
    private final Timer resultLabelFadeInTimer;
    private float resultLabelAlpha = 1.0f;

    public PolynomialCalculator() {
        super("Polynomial Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Set colors and fonts
        Color backgroundColor = new Color(240, 248, 255); // AliceBlue
        Color buttonColor = new Color(70, 130, 180); // SteelBlue
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        // Create top panel for input fields and output label
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding
        topPanel.setBackground(backgroundColor);
        input1Field = new JTextField();
        input2Field = new JTextField();
        JLabel poly1Label = new JLabel("<html><b>Polynomial 1:</b></html>");
        JLabel poly2Label = new JLabel("<html><b>Polynomial 2:</b></html>");
        poly1Label.setFont(labelFont);
        poly2Label.setFont(labelFont);
        topPanel.add(poly1Label);
        topPanel.add(input1Field);
        topPanel.add(poly2Label);
        topPanel.add(input2Field);
        add(topPanel, BorderLayout.NORTH);

        resultLabelFadeInTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultLabelAlpha < 1.0f) {
                    resultLabelAlpha += 0.03f;
                    if (resultLabelAlpha > 1.0f) {
                        resultLabelAlpha = 1.0f;
                        resultLabelFadeInTimer.stop();
                    }
                    resultLabel.repaint();
                }
            }
        });

        resultLabel = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, resultLabelAlpha));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        resultLabel.setFont(labelFont); // Increase font size
        resultLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding
        resultLabel.setBackground(backgroundColor);
        resultLabel.setOpaque(true);
        add(resultLabel, BorderLayout.CENTER);

        // Create bottom panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        buttonPanel.setBackground(backgroundColor);
        JButton addButton = createButton("Add", buttonColor, buttonFont);
        JButton subtractButton = createButton("Subtract", buttonColor, buttonFont);
        JButton multiplyButton = createButton("Multiply", buttonColor, buttonFont);
        JButton divideButton = createButton("Divide", buttonColor, buttonFont);
        JButton derivativeButton = createButton("Differentiate", buttonColor, buttonFont);
        JButton integralButton = createButton("Integrate", buttonColor, buttonFont);

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(derivativeButton);
        buttonPanel.add(integralButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String text, Color color, Font font) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ... handle actionPerformed here, as in previous versions

        String input1 = input1Field.getText().trim();
        String input2 = input2Field.getText().trim();

        Polynomial p1 = parsePolynomial(input1);
        Polynomial p2 = parsePolynomial(input2);

        String result;
        String operation;

        switch (e.getActionCommand()) {
            case "Add":
                result = String.valueOf(p1.add(p2));
                operation = "Sum: ";
                break;
            case "Subtract":
                result = String.valueOf(p1.subtract(p2));
                operation = "Difference: ";
                break;
            case "Multiply":
                result = String.valueOf(p1.multiply(p2));
                operation = "Product: ";
                break;
            case "Divide":
                try {
                    result = p1.divide(p2);
                    operation = "Division: ";
                } catch (ArithmeticException ex) {
                    resultLabel.setText("Division by zero polynomial not allowed.");
                    return;
                }
                break;
            case "Differentiate":
                result = String.valueOf(p1.differentiate());
                operation = "Derivative of Polynomial 1: ";
                break;
            case "Integrate":
                result = String.valueOf(p1.integrate());
                operation = "Integral of Polynomial 1: ";
                break;
            default:
                return;
        }



        // Fade-in animation for the result label
        resultLabelAlpha = 0.0f;
        resultLabelFadeInTimer.restart();
        resultLabel.setText(operation + result);
    }

    // ... implement parsePolynomial and other methods here
    private Polynomial parsePolynomial(String input) {
        Polynomial p = new Polynomial();

        if (input.isEmpty()) {
            return p;
        }

        String[] terms = input.split("\\s*(?=[+-])\\s*");

        for (String term : terms) {
            if (term.isEmpty()) {
                continue;
            }

            int coefficient;
            int exponent;

            if (term.equals("+")) {
                coefficient = 1;
                exponent = 0;
            } else if (term.equals("-")) {
                coefficient = -1;
                exponent = 0;
            } else {
                int indexOfX = term.indexOf("x");

                if (indexOfX == -1) {
                    coefficient = Integer.parseInt(term);
                    exponent = 0;
                } else {
                    String coefficientStr = term.substring(0, indexOfX).trim();
                    coefficient = coefficientStr.isEmpty() ? 1 : Integer.parseInt(coefficientStr);

                    int indexOfPower = term.indexOf("^");

                    if (indexOfPower == -1) {
                        exponent = 1;
                    } else {
                        exponent = Integer.parseInt(term.substring(indexOfPower + 1).trim());
                    }
                }
            }

            p.addTerm((double) coefficient, exponent);
        }

        return p;
    }
}

