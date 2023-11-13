package pong.model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class PlayerRectangle extends Rectangle {
    public PlayerRectangle(Player player) {
        this.translateXProperty().bind(player.positionProperty());
        this.widthProperty().bind(player.widthProperty());
        this.heightProperty().bind(player.heightProperty());
        this.fillProperty().bind(player.colorProperty());
        this.fillProperty().addListener((observable, oldValue, newValue) -> {
            this.setStroke(player.getColor().darker());
        });
        this.setArcWidth(16);
        this.setArcHeight(16);
        this.setStrokeWidth(3);
        this.setStrokeType(StrokeType.INSIDE);
    }
}
