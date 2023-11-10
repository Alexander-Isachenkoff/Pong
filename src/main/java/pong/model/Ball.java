package pong.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Ball {

    static final double START_SPEED = 200;
    private static final double ACC = 10;
    private final GameModel gameModel;
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final double size = 16;
    private double speed = START_SPEED;
    private double angle;

    Ball(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    void move(double seconds) {
        double radians = Math.toRadians(angle);

        double newX = x.get() + Math.cos(radians) * speed * seconds;
        if (newX <= 0 || newX >= gameModel.getWidth() - size) {
            newX = Math.min(Math.max(newX, 0), gameModel.getWidth() - size);
            angle = -angle + 180;
        }

        double newY = y.get() + Math.sin(radians) * speed * seconds;

        Player player1 = gameModel.getPlayer1();
        if (y.get() + size > gameModel.getHeight() - player1.getHeight()) {
            if (x.get() + size > player1.getPosition() && x.get() < player1.getPosition() + player1.getWidth()) {
                angle = -angle;
                if (player1.getCurrentSpeed() > 0) {
                    angle += (-180 - angle) / 3;
                }
                if (player1.getCurrentSpeed() < 0) {
                    angle += (0 - angle) / 3;
                }
                newY = gameModel.getHeight() - player1.getHeight() - size;
            }
        }

        Player player2 = gameModel.getPlayer2();
        if (y.get() < player2.getHeight()) {
            if (x.get() + size > player2.getPosition() && x.get() < player2.getPosition() + player2.getWidth()) {
                angle = -angle;
                if (player2.getCurrentSpeed() > 0) {
                    angle += (180 - angle) / 3;
                }
                if (player2.getCurrentSpeed() < 0) {
                    angle += (0 - angle) / 3;
                }
                newY = player2.getHeight();
            }
        }

        x.set(newX);
        y.set(newY);

        speed += ACC * seconds;
    }

    public ReadOnlyDoubleProperty xProperty() {
        return x;
    }

    public ReadOnlyDoubleProperty yProperty() {
        return y;
    }

    public double getX() {
        return x.get();
    }

    void setX(double value) {
        x.set(value);
    }

    double getY() {
        return y.get();
    }

    void setY(double value) {
        y.set(value);
    }

    double getSize() {
        return size;
    }

    void setAngle(double angle) {
        this.angle = angle;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }
}
