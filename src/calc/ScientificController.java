package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class ScientificController extends StandardController {

    @FXML
    public MenuBar menuBar;

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

    @FXML
    public void changeToStandard() throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("standardWindow.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
