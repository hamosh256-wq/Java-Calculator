import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

// ===== Calculator Logic =====
class Calculator {

    private double memory = 0;

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b != 0)
            return a / b;

        return 0;
    }

    public double modulo(double a, double b) {
        return a % b;
    }

    public double power(double a, double b) {
        return Math.pow(a, b);
    }

    public double sqrt(double a) {
        return Math.sqrt(a);
    }

    public double reciprocal(double a) {
        return 1 / a;
    }

    public double negate(double a) {
        return -a;
    }

    public double sin(double a) {
        return Math.sin(Math.toRadians(a));
    }

    public double cos(double a) {
        return Math.cos(Math.toRadians(a));
    }

    public double tan(double a) {
        return Math.tan(Math.toRadians(a));
    }

    public double log(double a) {
        return Math.log10(a);
    }

    public double getMemory() {
        return memory;
    }

    public void memoryAdd(double a) {
        memory += a;
    }

    public void memorySubtract(double a) {
        memory -= a;
    }

    public void memoryClear() {
        memory = 0;
    }
}

// ===== GUI =====
class CalculatorGUI {

    private Calculator calc = new Calculator();

    private JFrame frame;
    private JTextField display;
    private JTextArea history;

    private DecimalFormat df =
            new DecimalFormat("#.######");

    private double firstNumber = 0;
    private String operator = "";
    private boolean isOperatorPressed = false;

    public CalculatorGUI() {

        frame = new JFrame("Calculator GOD MODE OOP");
        frame.setSize(500, 750);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLayout(
                new BorderLayout(5, 5)
        );

        setupDisplay();
        setupHistory();
        setupButtons();

        frame.getContentPane().setBackground(
                new Color(25, 25, 25)
        );

        frame.setVisible(true);
    }

    // ===== DISPLAY =====
    private void setupDisplay() {

        display = new JTextField();

        display.setFont(
                new Font("Consolas",
                        Font.BOLD,
                        32)
        );

        display.setHorizontalAlignment(
                JTextField.RIGHT
        );

        display.setEditable(false);

        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);

        frame.add(display, BorderLayout.NORTH);
    }

    // ===== HISTORY =====
    private void setupHistory() {

        history = new JTextArea();

        history.setEditable(false);

        history.setFont(
                new Font("Consolas",
                        Font.PLAIN,
                        14)
        );

        history.setBackground(
                new Color(15, 15, 15)
        );

        history.setForeground(
                Color.LIGHT_GRAY
        );

        JScrollPane scroll =
                new JScrollPane(history);

        scroll.setPreferredSize(
                new Dimension(500, 150)
        );

        frame.add(scroll, BorderLayout.SOUTH);
    }

    // ===== BUTTONS =====
    private void setupButtons() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(8, 4, 5, 5)
        );

        panel.setBackground(
                new Color(25, 25, 25)
        );

        String[] buttons = {

                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0",".","=","+",

                "C","DEL","%","^",
                "√","1/x","+/-","π",

                "sin","cos","tan","log",
                "MC","MR","M+","M-"
        };

        for (String text : buttons) {

            JButton btn =
                    new JButton(text);

            btn.setFont(
                    new Font("Arial",
                            Font.BOLD,
                            18)
            );

            btn.setFocusable(false);

            btn.setBackground(
                    new Color(45, 45, 45)
            );

            btn.setForeground(Color.WHITE);

            btn.addMouseListener(
                    new MouseAdapter() {

                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            btn.setBackground(
                                    new Color(
                                            70,
                                            70,
                                            70
                                    )
                            );
                        }

                        public void mouseExited(
                                MouseEvent e
                        ) {

                            btn.setBackground(
                                    new Color(
                                            45,
                                            45,
                                            45
                                    )
                            );
                        }
                    }
            );

            btn.addActionListener(
                    new ActionListener() {

                        public void actionPerformed(
                                ActionEvent e
                        ) {

                            handleButtonPress(
                                    btn.getText()
                            );
                        }
                    }
            );

            panel.add(btn);
        }

        frame.add(panel, BorderLayout.CENTER);
    }

    // ===== BUTTON EVENTS =====
    private void handleButtonPress(
            String cmd
    ) {

        try {

            if (cmd.equals("C")) {

                display.setText("");

                firstNumber = 0;
                operator = "";
            }

            else if (cmd.equals("DEL")) {

                String s =
                        display.getText();

                if (s.length() > 0) {

                    display.setText(
                            s.substring(
                                    0,
                                    s.length() - 1
                            )
                    );
                }
            }

            else if (cmd.equals("π")) {

                display.setText(
                        String.valueOf(
                                Math.PI
                        )
                );
            }

            else if (cmd.equals("+/-")) {

                double x =
                        Double.parseDouble(
                                display.getText()
                        );

                display.setText(
                        df.format(
                                calc.negate(x)
                        )
                );
            }

            // ===== Unary =====

            else if (
                    cmd.equals("√") ||
                    cmd.equals("1/x") ||
                    cmd.equals("sin") ||
                    cmd.equals("cos") ||
                    cmd.equals("tan") ||
                    cmd.equals("log")
            ) {

                applyUnary(cmd);
            }

            // ===== Memory =====

            else if (cmd.equals("MC")) {

                calc.memoryClear();
            }

            else if (cmd.equals("MR")) {

                display.setText(
                        df.format(
                                calc.getMemory()
                        )
                );
            }

            else if (cmd.equals("M+")) {

                calc.memoryAdd(
                        Double.parseDouble(
                                display.getText()
                        )
                );
            }

            else if (cmd.equals("M-")) {

                calc.memorySubtract(
                        Double.parseDouble(
                                display.getText()
                        )
                );
            }

            // ===== Operators =====

            else if (
                    cmd.equals("+") ||
                    cmd.equals("-") ||
                    cmd.equals("*") ||
                    cmd.equals("/") ||
                    cmd.equals("%") ||
                    cmd.equals("^")
            ) {

                firstNumber =
                        Double.parseDouble(
                                display.getText()
                        );

                operator = cmd;

                isOperatorPressed = true;
            }

            // ===== Equal =====

            else if (cmd.equals("=")) {

                applyEqual();
            }

            // ===== Numbers =====

            else {

                if (isOperatorPressed) {

                    display.setText("");

                    isOperatorPressed =
                            false;
                }

                display.setText(
                        display.getText()
                                + cmd
                );
            }

        } catch (Exception ex) {

            display.setText("ERROR");
        }
    }

    // ===== Unary Operations =====
    private void applyUnary(
            String op
    ) {

        double x =
                Double.parseDouble(
                        display.getText()
                );

        double r = 0;

        switch (op) {

            case "√":
                r = calc.sqrt(x);
                break;

            case "1/x":
                r = calc.reciprocal(x);
                break;

            case "sin":
                r = calc.sin(x);
                break;

            case "cos":
                r = calc.cos(x);
                break;

            case "tan":
                r = calc.tan(x);
                break;

            case "log":
                r = calc.log(x);
                break;
        }

        history.append(
                op + "("
                        + df.format(x)
                        + ") = "
                        + df.format(r)
                        + "\n"
        );

        display.setText(
                df.format(r)
        );
    }

    // ===== Equal =====
    private void applyEqual() {

        if (operator.isEmpty())
            return;

        double secondNumber =
                Double.parseDouble(
                        display.getText()
                );

        double r = 0;

        switch (operator) {

            case "+":
                r = calc.add(
                        firstNumber,
                        secondNumber
                );
                break;

            case "-":
                r = calc.subtract(
                        firstNumber,
                        secondNumber
                );
                break;

            case "*":
                r = calc.multiply(
                        firstNumber,
                        secondNumber
                );
                break;

            case "/":
                r = calc.divide(
                        firstNumber,
                        secondNumber
                );
                break;

            case "%":
                r = calc.modulo(
                        firstNumber,
                        secondNumber
                );
                break;

            case "^":
                r = calc.power(
                        firstNumber,
                        secondNumber
                );
                break;
        }

        history.append(
                df.format(firstNumber)
                        + " "
                        + operator
                        + " "
                        + df.format(secondNumber)
                        + " = "
                        + df.format(r)
                        + "\n"
        );

        display.setText(
                df.format(r)
        );

        operator = "";
    }
}

// ===== MAIN =====
public class Main {

    public static void main(String[] args) {

        new CalculatorGUI();
    }

    }