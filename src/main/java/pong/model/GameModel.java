package pong.model;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.input.KeyCode;

import java.util.Random;
import java.util.function.Consumer;

public class GameModel {

    private static final int MAX_SCORE = 11;
    private final Player player1 = new Player(this);
    private final Player player2 = new Player(this);
    private final IntegerProperty player1Score = new SimpleIntegerProperty(-1);
    private final IntegerProperty player2Score = new SimpleIntegerProperty(-1);
    private final Ball ball = new Ball(this);
    private final ObservableSet<KeyCode> keysPressed = FXCollections.observableSet();
    private final double width = 500;
    private final double height = 500;
    private final AnimationTimer timer;
    private long lastUpdate;
    private Runnable onNewRound = () -> {
    };
    private Consumer<Player> onWin = (winner) -> {
    };

    public GameModel() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                }
                update(now);
            }
        };
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Ball getBall() {
        return ball;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    public void restart() {
        player1Score.set(0);
        player2Score.set(0);
        stop();
        start();
        newRound();
    }

    public void stop() {
        timer.stop();
    }

    public void start() {
        lastUpdate = 0;
        timer.start();
    }

    private void newRound() {
        Random random = new Random();
        player1.setPosition((getWidth() - player1.getWidth()) / 2);
        player2.setPosition((getWidth() - player2.getWidth()) / 2);
        ball.setSpeed(Ball.START_SPEED);
        ball.setX(getWidth() / 2);
        ball.setY(getHeight() / 2);
        ball.setAngle((45 + random.nextInt(90)) * (random.nextBoolean() ? 1 : -1));
        onNewRound.run();
    }

    private void update(long now) {
        long dt = now - lastUpdate;
        double dtSeconds = dt / 1e9;
        lastUpdate = now;

        if (keysPressed.contains(KeyCode.LEFT)) {
            getPlayer1().moveLeft();
        } else if (keysPressed.contains(KeyCode.RIGHT)) {
            getPlayer1().moveRight();
        } else {
            getPlayer1().stop();
        }

        if (keysPressed.contains(KeyCode.A)) {
            getPlayer2().moveLeft();
        } else if (keysPressed.contains(KeyCode.D)) {
            getPlayer2().moveRight();
        } else {
            getPlayer2().stop();
        }

        getPlayer1().move(dtSeconds);
        getPlayer2().move(dtSeconds);

        ball.move(dtSeconds);

        if (ball.getY() <= 0) {
            player1Score.set(player1Score.get() + 1);
            if (player1Score.get() == MAX_SCORE) {
                stop();
                onWin.accept(player1);
            } else {
                newRound();
            }
        }

        if (ball.getY() >= getHeight() - ball.getSize()) {
            player2Score.set(player2Score.get() + 1);
            if (player2Score.get() == MAX_SCORE) {
                stop();
                onWin.accept(player2);
            } else {
                newRound();
            }
        }
    }

    public ObservableSet<KeyCode> getKeysPressed() {
        return keysPressed;
    }

    public IntegerProperty player1ScoreProperty() {
        return player1Score;
    }

    public IntegerProperty player2ScoreProperty() {
        return player2Score;
    }

    public void setOnNewRound(Runnable onNewRound) {
        this.onNewRound = onNewRound;
    }

    public void setOnWin(Consumer<Player> onWin) {
        this.onWin = onWin;
    }
}
