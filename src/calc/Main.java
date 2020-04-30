package calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    Scene sceneStandard, sceneScientific;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("standardWindow.fxml"));
        loader.load();
        StandardController controller = loader.getController();
        primaryStage.setTitle("CalculatorFX");
        sceneStandard = new Scene(loader.getRoot(), 340, 390);
        primaryStage.setScene(sceneStandard);
        primaryStage.setResizable(false);
        buttonsPressed(controller);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void buttonsPressed(StandardController controller){
        sceneStandard.setOnKeyPressed((KeyEvent event) -> {
            KeyCode keyPressed = event.getCode();
            switch (keyPressed) {
                case DIGIT0:
                case NUMPAD0:
                    controller.btn0.fire();
                    break;
                case DIGIT1:
                case NUMPAD1:
                    controller.btn1.fire();
                    break;
                case DIGIT2:
                case NUMPAD2:
                    controller.btn2.fire();
                    break;
                case DIGIT3:
                case NUMPAD3:
                    controller.btn3.fire();
                    break;
                case DIGIT4:
                case NUMPAD4:
                    controller.btn4.fire();
                    break;
                case DIGIT5:
                case NUMPAD5:
                    controller.btn5.fire();
                    break;
                case DIGIT6:
                case NUMPAD6:
                    controller.btn6.fire();
                    break;
                case DIGIT7:
                case NUMPAD7:
                    controller.btn7.fire();
                    break;
                case DIGIT8:
                case NUMPAD8:
                    controller.btn8.fire();
                    break;
                case DIGIT9:
                case NUMPAD9:
                    controller.btn9.fire();
                    break;
                case SLASH:
                    controller.btnDivision.fire();
                    break;
                case EQUALS:
                case ENTER:
                    controller.btnEquals.fire();
                    break;
                case MINUS:
                    controller.btnSubtract.fire();
                    break;
                case COMMA:
                    controller.btnComma.fire();
                    break;
                case ASTERISK:
                    controller.btnMulti.fire();
                    break;
                case PLUS:
                    controller.btnAdd.fire();
                    break;

            }
        });
    }
}
