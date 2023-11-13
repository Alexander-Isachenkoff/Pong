package pong;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinController {

    public Label winLabel;
    private Runnable onMenu = () -> {};

    @FXML
    private void onMenu() {
        onMenu.run();
    }

    void setOnMenu(Runnable onMenu) {
        this.onMenu = onMenu;
    }
}
