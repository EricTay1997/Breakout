package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;

/**
 * Creates the paddle and handles the position, length and movement of the paddle.
 * Uses the Main class.
 * Depends on javafx and java.lang.Math packages.
 */
public class Paddle {
    private static final String PADDLE_IMAGE = "paddle.gif";
    private static final double PADDLE_WIDTH = 200;
    private static final double PADDLE_HEIGHT = 10;
    private static final double PADDLE_SHORTEN = 0.75;
    private static final double PADDLE_LENGTHEN = 1.5;

    private ImageView paddleImage;

    /**
     * Creates a paddle with a fixed image, height and width.
     */
    public Paddle() {
        paddleImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)));
        paddleImage.setFitHeight(PADDLE_HEIGHT);
        paddleImage.setFitWidth(PADDLE_WIDTH);
    }

    /**
     * Resets the paddle to the bottom-center of screen.
     * @param level current game level, determines how short the paddle is
     */
    public void resetPaddle(int level) {
        this.paddleImage.setY(Main.SIZE - PADDLE_HEIGHT);
        this.paddleImage.setFitWidth(PADDLE_WIDTH * Math.pow(PADDLE_SHORTEN, level));
        this.paddleImage.setX(Main.SIZE / 2 - this.paddleImage.getFitWidth() / 2);
    }

    /**
     * Increases the paddle length, while keeping the center constant.
     * This increase is the effect of a power up.
     */
    public void increasePaddleWidth() {
        double xCenter = this.paddleImage.getX() + this.paddleImage.getFitWidth() / 2;
        double newLength = this.paddleImage.getFitWidth() * PADDLE_LENGTHEN;
        this.paddleImage.setFitWidth(newLength);
        double newXCoordinate = xCenter - newLength / 2;
        this.paddleImage.setX(newXCoordinate);
    }

    /**
     * Get the paddle image.
     * @return paddle image
     */
    public ImageView getPaddleImage() {
        return this.paddleImage;
    }

    /**
     * Handles the movement of the paddle.
     * @param direction direction that the bouncer moves
     * @param speed increment in specificed direction
     */
    public void move(String direction, int speed) {
        if (direction == "left") {
            this.paddleImage.setX(this.paddleImage.getX() - speed);
        } else if (direction == "right") {
            this.paddleImage.setX(this.paddleImage.getX() + speed);
        } else if (direction == "up") {
            this.paddleImage.setY(this.paddleImage.getY() - speed);
        } else if (direction == "down") {
            this.paddleImage.setY(this.paddleImage.getY() + speed);
        }
    }
}
