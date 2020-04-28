package calc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    public Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnEquals, btnComma, btnAdd, btnSubtract, btnMulti, btnDivision, btnPercent;

    @FXML
    private Label result;

    private Stack<BigDecimal> operands = new Stack<>();
    private Stack<String> operators = new Stack<>();

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
        BigDecimal convertedNumber;
        if (!operator.equals("=")) {
            try {
                convertedNumber = BigDecimal.valueOf(Float.parseFloat(result.getText()));
                result.setText("");
                operands.push(convertedNumber);
                operators.push(operator);
            } catch (NumberFormatException numberFormatExc){
                System.out.println("Number format exception : " + numberFormatExc.getMessage());
                result.setText("NaN");
            }
        } else {
            convertedNumber = BigDecimal.valueOf(Float.parseFloat(result.getText()));
            operands.push(convertedNumber);
            MathContext mc = new MathContext(12,RoundingMode.HALF_UP);
            while (operands.size() > 1) {
                String currentOperator = operators.pop();
                switch (currentOperator) {
                    case "+":
                        operands.push(operands.pop().add(operands.pop(),mc));
                        break;
                    case "-":
                        BigDecimal v1 = operands.pop();
                        BigDecimal v2 = operands.pop();
                        operands.push(v2.subtract(v1,mc).stripTrailingZeros());
                        break;
                    case "*":
                        operands.push(operands.pop().multiply(operands.pop(),mc).stripTrailingZeros());
                        break;
                    case "/":
                        try {
                            v1 = operands.pop();
                            v2 = operands.pop();
                            if (v1.equals(BigDecimal.ZERO)){
                                operands.push(BigDecimal.ZERO);
                            } else {
                                operands.push(v2.divide(v1,mc).stripTrailingZeros());
                            }
                        } catch (ArithmeticException arithmetic){
                            System.out.println("Arithmetic exception : " + arithmetic.getMessage());
                        }
                        break;
                }
            }
            result.setText(operands.pop().toPlainString());
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

    @FXML
    public void handleExit(){
        Platform.exit();
    }

    @FXML
    public void showAbout(){
        Dialog<ButtonType> aboutDialog = new Dialog<>();
        aboutDialog.setTitle("About");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("aboutDialog.fxml"));
        try {
            aboutDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioe){
            System.out.println("Couldn't load dialog: " + ioe.getMessage());
            ioe.printStackTrace();
            return;
        }
        aboutDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = aboutDialog.showAndWait();
    }

}
