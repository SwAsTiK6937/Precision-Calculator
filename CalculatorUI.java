import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class CalculatorUI extends JFrame {
    private JTextField display;
    private JTextArea historyArea;
    private Calculator calculator;
    private BigDecimal firstValue, secondValue;
    private String operator;
    private boolean isDecimalEntered, isNewNumber;
    public CalculatorUI() {
        calculator = new Calculator();
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));
        String[] buttons = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", ".", "=", "/",
            "CE", "C"
        };
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }
        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(new JScrollPane(historyArea), BorderLayout.EAST);
        add(panel, BorderLayout.CENTER);
        setTitle("Precision Calculator");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        isNewNumber = true;
    }
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("0123456789".contains(command)) {
                if (isNewNumber) {
                    display.setText(command);
                    isNewNumber = false;
                } else {
                    display.setText(display.getText() + command);
                }
            } else if (".".equals(command)) {
                if (!isDecimalEntered) {
                    if (isNewNumber) {
                        display.setText("0.");
                        isNewNumber = false;
                    } else {
                        display.setText(display.getText() + ".");
                    }
                    isDecimalEntered = true;
                }
            } else if ("+-*/".contains(command)) {
                firstValue = new BigDecimal(display.getText());
                operator = command;
                isNewNumber = true;
                isDecimalEntered = false;
                historyArea.append(firstValue + " " + operator + " ");
            } else if ("=".equals(command)) {
                try {
                    secondValue = new BigDecimal(display.getText());
                    BigDecimal result = BigDecimal.ZERO;
                    switch (operator) {
                        case "+":
                            result = calculator.add(firstValue, secondValue);
                            break;
                        case "-":
                            result = calculator.subtract(firstValue, secondValue);
                            break;
                        case "*":
                            result = calculator.multiply(firstValue, secondValue);
                            break;
                        case "/":
                            result = calculator.divide(firstValue, secondValue);
                            break;
                    }
                    display.setText(result.toString());
                    historyArea.append(secondValue + " = " + result + "\n");
                    firstValue = result;
                    isNewNumber = true;
                    isDecimalEntered = false;
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            } else if ("C".equals(command)) {
                display.setText("");
                isNewNumber = true;
                isDecimalEntered = false;
            } else if ("CE".equals(command)) {
                firstValue = null;
                secondValue = null;
                operator = null;
                display.setText("");
                isNewNumber = true;
                isDecimalEntered = false;
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorUI().setVisible(true);
        });
    }
}
