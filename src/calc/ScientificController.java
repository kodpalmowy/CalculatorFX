package calc;

import ch.obermuhlner.math.big.BigDecimalMath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ScientificController extends StandardController {

    @FXML
    public MenuBar menuBar;

    MathContext mc2 = new MathContext(17, RoundingMode.HALF_UP);

    @Override
    public void handleNumbers(ActionEvent e) {
        super.handleNumbers(e);
    }

    @Override
    public void handleOperator(ActionEvent e) {
        super.handleOperator(e);
    }

    @Override
    public void handleClear() {
        super.handleClear();
    }

    @Override
    public void handleSwitch() {
        super.handleSwitch();
    }

    @Override
    public void handleComma() {
        super.handleComma();
    }

    @Override
    public void handlePercent() {
        super.handlePercent();
    }

    @Override
    public void handleExit() {
        super.handleExit();
    }

    @Override
    public void showAbout() {
        super.showAbout();
    }
    /**
     * This method handles changing the scene to Standard View
     **/
    @FXML
    public void changeToStandard() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("standardWindow.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method handles calculating sin(value)
     * @value is in [rad]
     **/
    @FXML
    public void handleSin() {
        if (!result.getText().equals("NaN")) {
            double value = Double.parseDouble(result.getText());
            value = Math.toRadians(value);
            result.setText(String.valueOf(Math.sin(value)));
        }
    }
    /**
     * This method handles calculating cos(value)
     * @value is in [rad]
     **/
    @FXML
    public void handleCos() {
        if (!result.getText().equals("NaN")) {
            double value = Double.parseDouble(result.getText());
            value = Math.toRadians(value);
            result.setText(String.valueOf(Math.cos(value)));
        }
    }
    /**
     * This method handles calculating tan(value)
     * @value is in [rad]
     **/
    @FXML
    public void handleTan() {
        if (!result.getText().equals("NaN")) {
            double value = Double.parseDouble(result.getText());
            value = Math.toRadians(value);
            result.setText(String.valueOf(Math.tan(value)));
        }
    }
    /**
     * This method handles calculating square root
     **/
    @FXML
    public void handleSqrt() {
        if (!result.getText().equals("NaN")) {
            double value = Double.parseDouble(result.getText());
            result.setText(String.valueOf(Math.sqrt(value)));
        }
    }
    /**
     * This method handles calculating root of 3
     **/
    @FXML
    public void handleRoot3() {
        if (!result.getText().equals("NaN")) {
            BigDecimal value = BigDecimal.valueOf(Double.parseDouble(result.getText()));
            BigDecimal root3 = new BigDecimal(3);
            result.setText(BigDecimalMath.root(value, root3, mc2).stripTrailingZeros().toPlainString());
        }
    }
    /**
     * This method handles calculating 1 divided by x
     **/
    @FXML
    public void handleOneDivX() {
        if (!result.getText().equals("NaN")) {
            BigDecimal x = new BigDecimal(result.getText());
            x = BigDecimal.ONE.divide(x, mc2).stripTrailingZeros();
            result.setText(x.toPlainString());
        }
    }
    /**
     * This method handles calculating power of 2
     **/
    @FXML
    public void handlePow2() {
        if (!result.getText().equals("NaN")) {
            BigDecimal x = new BigDecimal(result.getText());
            x = x.pow(2).stripTrailingZeros();
            result.setText(x.toPlainString());
        }
    }
    /**
     * This method handles calculating power of 3
     **/
    @FXML
    public void handlePow3() {
        if (!result.getText().equals("NaN")) {
            BigDecimal x = new BigDecimal(result.getText());
            x = x.pow(3).stripTrailingZeros();
            result.setText(x.toPlainString());
        }
    }
    /**
     * This method handles calculating exponent power of x
     **/
    @FXML
    public void handleExpPow() {
        if (!result.getText().equals("NaN")) {
            BigDecimal x = new BigDecimal(result.getText());
            BigDecimal e = BigDecimalMath.e(mc2);
            BigDecimal pow = BigDecimalMath.pow(x, e, mc2).stripTrailingZeros();
            result.setText(pow.toPlainString());
        }
    }
    /**
     * This method handles printing value of PI
     * PI = 3,1415926535897932 for this app
     **/
    @FXML
    public void handlePi() {
        result.setText(BigDecimalMath.pi(mc2).toPlainString());
    }
    /**
     * This method handles printing value of exponent
     * exponent = 2,7182818284590452 for this app
     **/
    @FXML
    public void handleExp() {
        result.setText(BigDecimalMath.e(mc2).toPlainString());
    }
    /**
     * This method handles calculating natural logarithm
     **/
    @FXML
    public void handleLogN() {
        if (!result.getText().equals("NaN")) {
            BigDecimal x = new BigDecimal(result.getText());
            x = BigDecimalMath.log(x, mc2).stripTrailingZeros();
            result.setText(x.toPlainString());
        }
    }
    /**
     * This method handles calculating decimal logarithm
     **/
    @FXML
    public void handleLog10() {
        if (!result.getText().equals("NaN")) {
            BigDecimal x = new BigDecimal(result.getText());
            x = BigDecimalMath.log10(x, mc2).stripTrailingZeros();
            result.setText(x.toPlainString());
        }
    }
}
