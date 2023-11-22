package pong.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class PlayerRectangle extends Rectangle {
    public PlayerRectangle(Player player) {
        this.translateXProperty().bind(player.positionProperty());
        this.widthProperty().bind(player.widthProperty());
        this.heightProperty().bind(player.heightProperty());
        this.fillProperty().bind(player.colorProperty());
        this.fillProperty().addListener((observable, oldValue, newValue) -> {
            Color stroke;
            if (player.getColor().getBrightness() > 0.5) {
                stroke = player.getColor().darker();
            } else {
                stroke = player.getColor().brighter();
            }
            this.setStroke(stroke);
        });
        this.setArcWidth(16);
        this.setArcHeight(16);
        this.setStrokeWidth(3);
        this.setStrokeType(StrokeType.INSIDE);
    }
}
