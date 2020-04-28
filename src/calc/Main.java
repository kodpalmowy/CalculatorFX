package calc;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("CalculatorFX");
        Scene scene = new Scene(loader.getRoot(), 400, 450);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.setOnKeyPressed((KeyEvent event) -> {
            KeyCode keyPressed = event.getCode();
            switch (keyPressed) {
                case DIGIT0:
                    controller.btn0.fire();
                    break;
                case DIGIT1:
                    controller.btn1.fire();
                    break;
                case DIGIT2:
                    controller.btn2.fire();
                    break;
                case DIGIT3:
                    controller.btn3.fire();
                    break;
                case DIGIT4:
                    controller.btn4.fire();
                    break;
                case DIGIT5:
                    controller.btn5.fire();
                    break;
                case DIGIT6:
                    controller.btn6.fire();
                    break;
                case DIGIT7:
                    controller.btn7.fire();
                    break;
                case DIGIT8:
                    controller.btn8.fire();
                    break;
                case DIGIT9:
                    controller.btn9.fire();
                    break;
                case SLASH:
                    controller.btnDivision.fire();
                    break;
                case EQUALS:
                    controller.btnEquals.fire();
                    break;
                case MINUS:
                    controller.btnSubtract.fire();
                    break;
                case COMMA:
                    controller.btnComma.fire();
                    break;

            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
