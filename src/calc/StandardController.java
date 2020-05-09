package calc;

import ch.obermuhlner.math.big.BigDecimalMath;
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
    public Label result;

    @FXML
    private GridPane rootGridPane;

    public Stack<BigDecimal> operands = new Stack<>();
    public Stack<String> operators = new Stack<>();
    public MathContext mc = new MathContext(11, RoundingMode.HALF_UP);
    public MathContext mcScientific = new MathContext(17, RoundingMode.HALF_UP);

    /**
     * Handles button inputs for numbers
     **/
    @FXML
    public void handleNumbers(ActionEvent e) {
        Button number = (Button) e.getSource();
        if (result.getText().equals("0") || result.getText().equals("NaN")) {
            result.setText(number.getText());
        } else {
            result.setText(result.getText() + number.getText());
        }
    }

    /**
     * Handles button inputs for operators
     **/
    @FXML
    public void handleOperator(ActionEvent e) {
        Button operator = (Button) e.getSource();
        BigDecimal convertedNumber;
        if (operator.equals(btnEquals)) {
            try {
                convertedNumber = BigDecimal.valueOf(Float.parseFloat(result.getText()));
                result.setText("");
                operands.push(convertedNumber);
                operators.push(operator.getAccessibleText());
            } catch (NumberFormatException numberFormatExc) {
                System.out.println("Number format exception : " + numberFormatExc.getMessage());
                result.setText("NaN");
            }
        } else {
            try {
                convertedNumber = BigDecimal.valueOf(Float.parseFloat(result.getText()));
                operands.push(convertedNumber);
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
                        case "y√x":
                            v1 = operands.pop();
                            v2 = operands.pop();
                            operands.push(BigDecimalMath.root(v2, v1, mcScientific).stripTrailingZeros());
                            break;
                        case "x^y":
                            v1 = operands.pop();
                            v2 = operands.pop();
                            operands.push(BigDecimalMath.pow(v2, v1, mcScientific).stripTrailingZeros());
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.setTitle("About");
        Hyperlink github = new Hyperlink();
        github.setText("https://www.github.com/kodpalmowy");
        github.setAccessibleText("kodpalmowy");

        alert.setContentText("Creator:\nGitHub: " + github.getAccessibleText());
        DialogPane aboutDialog = alert.getDialogPane();
        aboutDialog.getStylesheets().add(
                getClass().getResource("/calc/css/stylescss.css").toExternalForm());
        aboutDialog.getStyleClass().add("dialog-pane");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("aboutDialog.fxml"));
        alert.showAndWait();
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
