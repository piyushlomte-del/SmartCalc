package com.smartcalc.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SmartCalculatorUI extends JFrame {
    private final JTextField display;
    private final JTextArea historyArea;
    private final java.util.List<String> history = new ArrayList<>();

    public SmartCalculatorUI() {
        setTitle("⚡ Smart Calculator");
        setSize(420, 480);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, 420, 480, 25, 25));
        setLayout(new BorderLayout());

        // 🌌 Gradient Background
        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 20),
                        0, getHeight(), new Color(0, 40, 70));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new BorderLayout());
        add(bgPanel);

        // 🧠 Display
        display = new JTextField();
        display.setFont(new Font("Consolas", Font.BOLD, 26));
        display.setEditable(false);
        display.setBackground(new Color(20, 20, 40));
        display.setForeground(new Color(0, 255, 255));
        display.setCaretColor(Color.WHITE);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        bgPanel.add(display, BorderLayout.NORTH);

        // 📜 History
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(15, 20, 30));
        historyArea.setForeground(new Color(100, 255, 200));
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setPreferredSize(new Dimension(140, 0));
        bgPanel.add(historyScroll, BorderLayout.EAST);

        // 🔢 Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 8, 8));
        buttonPanel.setOpaque(false);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "C",
                "√", "x²", "1/x", "⌫"
        };

        for (String text : buttons) {
            final String btnText = text;
            JButton btn = createNeonButton(btnText);
            buttonPanel.add(btn);
        }

        bgPanel.add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // ✨ Futuristic Button
    private JButton createNeonButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Orbitron", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(25, 25, 50));
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 1, true));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 80, 100));
                btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2, true));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(25, 25, 50));
                btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 1, true));
            }
        });

        btn.addActionListener(e -> handleButton(text));
        return btn;
    }

    // 💡 Logic
    private void handleButton(String text) {
        switch (text) {
            case "C" -> display.setText("");
            case "⌫" -> {
                String current = display.getText();
                if (!current.isEmpty()) display.setText(current.substring(0, current.length() - 1));
            }
            case "=" -> evaluateExpression();
            case "sin", "cos", "tan", "√", "x²", "1/x" -> applyFunction(text);
            default -> display.setText(display.getText() + text);
        }
    }

    private void applyFunction(String func) {
        try {
            double val = Double.parseDouble(display.getText());
            double res = switch (func) {
                case "sin" -> Math.sin(Math.toRadians(val));
                case "cos" -> Math.cos(Math.toRadians(val));
                case "tan" -> Math.tan(Math.toRadians(val));
                case "√" -> Math.sqrt(val);
                case "x²" -> Math.pow(val, 2);
                case "1/x" -> 1 / val;
                default -> 0;
            };
            display.setText(String.valueOf(res));
            addToHistory(func + "(" + val + ") = " + res);
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void evaluateExpression() {
        try {
            String exp = display.getText();
            double result = evaluate(exp);
            display.setText(String.valueOf(result));
            addToHistory(exp + " = " + result);
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void addToHistory(String record) {
        history.add(record);
        historyArea.setText(String.join("\n", history));
    }

    private double evaluate(String expression) {
        Stack<Double> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        StringBuilder num = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') num.append(c);
            else {
                if (num.length() > 0) {
                    nums.push(Double.parseDouble(num.toString()));
                    num.setLength(0);
                }
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c))
                    nums.push(applyOp(ops.pop(), nums.pop(), nums.pop()));
                ops.push(c);
            }
        }
        if (num.length() > 0) nums.push(Double.parseDouble(num.toString()));
        while (!ops.isEmpty()) nums.push(applyOp(ops.pop(), nums.pop(), nums.pop()));
        return nums.pop();
    }

    private int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    private double applyOp(char op, double b, double a) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> 0;
        };
    }
}
