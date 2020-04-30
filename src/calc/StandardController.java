package calc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardController {
    @FXML
    public Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnEquals, btnComma, btnAdd, btnSubtract, btnMulti, btnDivision, btnPercent;
    @FXML
    public MenuItem scientific;

    @FXML
    public MenuBar menuBar;

    @FXML
    private Label result;

    @FXML
    private GridPane rootGridPane;

    private Stack<BigDecimal> operands = new Stack<>();
    private Stack<String> operators = new Stack<>();

    /**
     * Handles button inputs for numbers
     **/
    @FXML
    public void handleNumbers(ActionEvent e) {
        String number = ((Button) e.getSource()).getText();
        if (result.getText().equals("0") || result.getText().equals("NaN")) {
            result.setText(number);
        } else {
            result.setText(result.getText() + number);
        }
    }

    /**
     * Handles button inputs for operators
     **/
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
            } catch (NumberFormatException numberFormatExc) {
                System.out.println("Number format exception : " + numberFormatExc.getMessage());
                result.setText("NaN");
            }
        } else {
            try {
                convertedNumber = BigDecimal.valueOf(Float.parseFloat(result.getText()));
                operands.push(convertedNumber);
                MathContext mc = new MathContext(11, RoundingMode.HALF_UP);
                while (operands.size() > 1) {
                    String currentOperator = operators.pop();
                    switch (currentOperator) {
                        case "+":
                            operands.push(operands.pop().add(operands.pop(), mc).stripTrailingZeros());
                            break;
                        case "-":
                            BigDecimal v1 = operands.pop();
                            BigDecimal v2 = operands.pop();
                            operands.push(v2.subtract(v1, mc).stripTrailingZeros());
                            break;
                        case "*":
                            operands.push(operands.pop().multiply(operands.pop(), mc).stripTrailingZeros());
                            break;
                        case "/":
                            try {
                                v1 = operands.pop();
                                v2 = operands.pop();
                                if (v1.equals(BigDecimal.ZERO)) {
                                    operands.push(BigDecimal.ZERO);
                                } else {
                                    operands.push(v2.divide(v1, mc).stripTrailingZeros());
                                }
                            } catch (ArithmeticException arithmetic) {
                                System.out.println("Arithmetic exception : " + arithmetic.getMessage());
                                result.setText("NaN");
                            }
                            break;
                    }
                }
                result.setText(operands.pop().toPlainString());
            } catch (EmptyStackException stackException) {
                System.out.println("Empty stack exception : " + stackException.getMessage());
                // result of division by zero
            } catch (NumberFormatException formatException) {
                System.out.println("Number format exception : " + formatException.getMessage());
            }
        }

    }

    /**
     * Handles cleaning Label with input numbers
     **/
    @FXML
    public void handleClear() {
        result.setText("0");
    }

    /**
     * Handles changing numbers with +/-
     **/
    @FXML
    public void handleSwitch() {
        if (!result.getText().equals("NaN")) {
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
    }

    /**
     * Handles adding comma to number
     **/
    @FXML
    public void handleComma() {
        if (!result.getText().equals("NaN")) {
            Pattern pattern = Pattern.compile(".*\\..*");
            Matcher matcher = pattern.matcher(result.getText());
            if (!matcher.matches()) {
                result.setText(result.getText() + ".");
            }
        }
    }

    /**
     * Handles percentage calculation
     **/
    @FXML
    public void handlePercent() {
        float value = Float.parseFloat(result.getText()) / 100;
        result.setText(String.valueOf(value));
    }

    /**
     * Handles exiting app form menu bar item
     **/
    @FXML
    public void handleExit() {
        Platform.exit();
    }

    /**
     * Handles showing dialog with info about me
     **/
    @FXML
    public void showAbout() {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        rootGridPane.setEffect(blur);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("About");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("aboutDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioe){
            System.out.println("Couldn't load dialog : " + ioe.getMessage());
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();

        rootGridPane.setEffect(null);
    }

    /**
     * This method handles changing the scene to Scientific View
     **/
    @FXML
    public void changeToScientific() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("scientificWindow.fxml"));
        Scene scene = new Scene(parent);
        // This method gets the Stage
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
