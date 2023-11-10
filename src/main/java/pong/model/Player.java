package pong.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Player {

    private final GameModel gameModel;
    private final DoubleProperty position = new SimpleDoubleProperty();
    private final DoubleProperty width = new SimpleDoubleProperty(80);
    private final DoubleProperty height = new SimpleDoubleProperty(16);
    private final double speed = 300;
    private double currentSpeed;

    Player(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public ReadOnlyDoubleProperty positionProperty() {
        return position;
    }

    void move(double seconds) {
        setPosition(position.get() + currentSpeed * seconds);
    }

    void moveLeft() {
        currentSpeed = -speed;
    }

    void moveRight() {
        currentSpeed = speed;
    }

    void stop() {
        currentSpeed = 0;
    }

    double getPosition() {
        return position.get();
    }

    void setPosition(double value) {
        position.set(Math.max(Math.min(gameModel.getWidth() - getWidth(), value), 0));
    }

    double getCurrentSpeed() {
        return currentSpeed;
    }

    public ReadOnlyDoubleProperty widthProperty() {
        return width;
    }

    public ReadOnlyDoubleProperty heightProperty() {
        return height;
    }

    double getWidth() {
        return width.get();
    }

    double getHeight() {
        return height.get();
    }

}
