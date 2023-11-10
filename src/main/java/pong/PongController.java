package pong;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import pong.model.GameModel;

public class PongController {

    private final GameModel gameModel = new GameModel();
    public Rectangle p1Rect;
    public Rectangle p2Rect;
    public Group ball;
    public Label p1Score;
    public Label p2Score;
    public Label p1Name;
    public Label p2Name;

    @FXML
    private void initialize() {
        p1Rect.sceneProperty().addListener((observable, oldValue, scene) -> {
            scene.setOnKeyPressed(event -> {
                gameModel.getKeysPressed().add(event.getCode());
            });
            scene.setOnKeyReleased(event -> {
                gameModel.getKeysPressed().remove(event.getCode());
            });
        });

        p1Rect.translateXProperty().bind(gameModel.getPlayer1().positionProperty());
        p2Rect.translateXProperty().bind(gameModel.getPlayer2().positionProperty());
        p1Rect.widthProperty().bind(gameModel.getPlayer1().widthProperty());
        p2Rect.widthProperty().bind(gameModel.getPlayer2().widthProperty());
        p1Rect.heightProperty().bind(gameModel.getPlayer1().heightProperty());
        p2Rect.heightProperty().bind(gameModel.getPlayer2().heightProperty());

        ball.translateXProperty().bind(gameModel.getBall().xProperty());
        ball.translateYProperty().bind(gameModel.getBall().yProperty());

        gameModel.player1ScoreProperty().addListener((observable, oldValue, newValue) -> {
            p1Score.setText(String.valueOf(newValue));
        });
        gameModel.player2ScoreProperty().addListener((observable, oldValue, newValue) -> {
            p2Score.setText(String.valueOf(newValue));
        });

        gameModel.start();
    }
}
