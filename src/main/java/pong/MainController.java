package pong;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainController {

    public TextField name1;
    public TextField name2;
    public ColorPicker color1;
    public ColorPicker color2;

    @FXML
    private void initialize() {
        color1.setValue(Color.color(1, 0, 0));
        color2.setValue(Color.color(0, 1, 0));
    }

    @FXML
    private void onPlay() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("pong.fxml"));
        name1.getScene().setRoot(loader.load());
        PongController controller = loader.getController();

        controller.setPlayer1Name(name1.getText());
        controller.setPlayer2Name(name2.getText());

        controller.setPlayer1Color(color1.getValue());
        controller.setPlayer2Color(color2.getValue());
    }

}
