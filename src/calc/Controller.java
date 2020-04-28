package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    public Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnEquals, btnComma, btnAdd, btnSubtract, btnMulti, btnDivision, btnPercent;

    @FXML
    private Label result;

    private float v1, v2;

    private Stack<Float> operands = new Stack<>();
    private Stack<String> operators = new Stack<>();

    private DecimalFormatSymbols dotSymbol = new DecimalFormatSymbols(Locale.US);
    private DecimalFormat format = new DecimalFormat("#.###############", dotSymbol);

    @FXML
    public void handleNumbers(ActionEvent e) {
        String number = ((Button) e.getSource()).getText();
        if (result.getText().equals("0")) {
            result.setText(number);
        } else {
            result.setText(result.getText() + number);
        }
    }

    @FXML
    public void handleOperator(ActionEvent e) {
        String operator = ((Button) e.getSource()).getText();
        float convertedNumber;
        if (!operator.equals("=")) {
            convertedNumber = Float.parseFloat(result.getText());
            result.setText("");
            operands.push(convertedNumber);
            operators.push(operator);
        } else {
            convertedNumber = Float.parseFloat(result.getText());
            operands.push(convertedNumber);
            while (operands.size() > 1) {
                String currentOperator = operators.pop();
                switch (currentOperator) {
                    case "+":
                        operands.push(operands.pop() + operands.pop());
                        break;
                    case "-":
                        v1 = operands.pop();
                        v2 = operands.pop();
                        operands.push(v2 - v1);
                        break;
                    case "*":
                        operands.push(operands.pop() * operands.pop());
                        break;
                    case "/":
                        v1 = operands.pop();
                        v2 = operands.pop();
                        operands.push(v2 / v1);
                        break;
                }
            }
            result.setText(format.format(operands.pop()));
        }

    }

    @FXML
    public void handleClear() {
        result.setText("0");
    }

    @FXML
    public void handleSwitch() {
        Pattern pattern = Pattern.compile("-.*");
        Matcher matcher = pattern.matcher(result.getText());
        if (!result.getText().isEmpty()) {
            if (!matcher.matches()) {
                result.setText("-" + result.getText());
            } else {
                result.setText(result.getText().substring(1));
            }
        }
    }

    @FXML
    public void handleComma() {
        Pattern pattern = Pattern.compile(".*\\..*");
        Matcher matcher = pattern.matcher(result.getText());
        if (!matcher.matches()) {
            result.setText(result.getText() + ".");
        }
    }

    @FXML
    public void handlePercent() {
        float value = Float.parseFloat(result.getText()) / 100;
        result.setText(String.valueOf(value));
    }
}
