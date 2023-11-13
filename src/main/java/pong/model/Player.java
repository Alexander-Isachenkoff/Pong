package pong.model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class Player {

    private final GameModel gameModel;
    private final DoubleProperty position = new SimpleDoubleProperty();
    private final DoubleProperty width = new SimpleDoubleProperty(80);
    private final DoubleProperty height = new SimpleDoubleProperty(16);
    private final double speed = 300;
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<Color> color = new SimpleObjectProperty<>();
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

    public ReadOnlyObjectProperty<Color> colorProperty() {
        return color;
    }

    public Color getColor() {
        return color.get();
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
