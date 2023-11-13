package pong;

import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pong.model.GameModel;

import java.io.IOException;

public class PongController {

    private final GameModel gameModel = new GameModel();
    public Rectangle p1Rect;
    public Rectangle p2Rect;
    public Group ball;
    public Label p1Score;
    public Label p2Score;
    public Label p1Name;
    public Label p2Name;
    public AnchorPane gamePane;

    @FXML
    private void initialize() {
        p1Rect.sceneProperty().addListener((observable, oldValue, scene) -> {
            if (scene != null) {
                scene.setOnKeyPressed(event -> {
                    gameModel.getKeysPressed().add(event.getCode());
                });
                scene.setOnKeyReleased(event -> {
                    gameModel.getKeysPressed().remove(event.getCode());
                });
            }
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

        gameModel.setOnNewRound(this::onNewRound);

        gameModel.setOnWin(winner -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            String playerName;
            if (winner == gameModel.getPlayer1()) {
                playerName = p1Name.getText();
            } else {
                playerName = p2Name.getText();
            }
            alert.setHeaderText(playerName + " wins!");
            alert.show();
            alert.setOnHidden(event -> toMenu());
        });

        gameModel.restart();
    }

    private void onNewRound() {
        int pause = 3;
        SequentialTransition sq = new SequentialTransition();
        for (int i = pause; i > 0; i--) {
            Label label = new Label(String.valueOf(i));
            label.getStyleClass().add("timer-label");
            AnchorPane.setTopAnchor(label, 0.0);
            AnchorPane.setBottomAnchor(label, 0.0);
            AnchorPane.setLeftAnchor(label, 0.0);
            AnchorPane.setRightAnchor(label, 0.0);
            label.setScaleX(0);
            label.setScaleY(0);

            gamePane.getChildren().add(label);

            ScaleTransition st = new ScaleTransition(Duration.millis(200), label);
            st.setToX(1);
            st.setToY(1);

            ParallelTransition pt = new ParallelTransition(new PauseTransition(Duration.seconds(1)), st);
            sq.getChildren().add(pt);
            pt.setOnFinished(event -> gamePane.getChildren().remove(label));
        }
        sq.setOnFinished(event -> gameModel.start());
        gameModel.stop();
        sq.play();
    }

    void setPlayer1Name(String name) {
        p1Name.setText(name);
    }

    void setPlayer2Name(String name) {
        p2Name.setText(name);
    }

    void setPlayer1Color(Color color) {
        p1Name.setTextFill(color);
        p1Rect.setFill(color);
    }

    void setPlayer2Color(Color color) {
        p2Name.setTextFill(color);
        p2Rect.setFill(color);
    }

    @FXML
    private void onMenu() {
        toMenu();
    }

    private void toMenu() {
        gameModel.stop();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        try {
            p1Score.getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainController controller = loader.getController();
        controller.name1.setText(p1Name.getText());
        controller.name2.setText(p2Name.getText());
        controller.color1.setValue((Color) p1Rect.getFill());
        controller.color2.setValue((Color) p2Rect.getFill());
    }

}
