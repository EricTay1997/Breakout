package breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creates power ups.
 * Responsible for dropping power ups when power brick is broken.
 * Responsible for their effects when the paddle catches these power ups.
 * Depends on the Ball and Paddle classes.
 * Uses javafx.scene packages.
 */
public class PowerUp {
    private static final String SLOW_BOUNCER_IMAGE = "sizepower.gif";
    private static final String LONG_PADDLE_IMAGE = "pointspower.gif";
    private static final String HEAVY_BALL_IMAGE = "extraballpower.gif";
    private static final String EXTRA_LIFE_IMAGE = "laserpower.gif";
    private static final int NO_POWER = 0;
    private static final int SLOW_BOUNCER = 1;
    private static final int LONG_PADDLE = 2;
    private static final int HEAVY_BALL = 3;
    private static final int EXTRA_LIFE = 4;
    public static final int MIN_POWER_UP = SLOW_BOUNCER;
    public static final int MAX_POWER_UP = EXTRA_LIFE;
    private static final int POWER_UP_SPEED = 200;

    private double ballSpeed;
    private ImageView ballImage;
    private int powerUp;

    /**
     * Creates power up.
     * Assumes that user inputs a valid power (integer from 1 to 4).
     * @param power will determine the power up effect
     */
    public PowerUp(int power) {
        ballImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(chooseImage(power))));
        powerUp = power;
    }

    private String chooseImage(int power) {
        if (power == SLOW_BOUNCER) {
            return SLOW_BOUNCER_IMAGE;
        } else if (power == LONG_PADDLE) {
            return LONG_PADDLE_IMAGE;
        } else if (power == HEAVY_BALL) {
            return HEAVY_BALL_IMAGE;
        } else if (power == EXTRA_LIFE) {
            return EXTRA_LIFE_IMAGE;
        }
        return "";
    }

    /**
     * Set the position of the power up.
     * Assumes that input is valid (on-screen)/accurate.
     * @param xPosition
     * @param yPosition
     */
    public void setPosition(double xPosition, double yPosition) {
        this.ballImage.setX(xPosition);
        this.ballImage.setY(yPosition);
    }

    /**
     * Sets the power up to drop when the power brick is broken.
     */
    public void initiatePowerUpDrop() {
        this.ballSpeed = POWER_UP_SPEED;
    }

    /**
     * Handles the falling of power ups when the power up brick is broken.
     * @param elapsedTime
     * @param myPaddle
     * @param root Group that nodes are added to
     * @param myBouncer The bouncer in the game
     */
    public void powerUpDrop(double elapsedTime, Paddle myPaddle, Group root, Ball myBouncer) {
        this.ballImage.setY(this.ballImage.getY() + this.ballSpeed * elapsedTime);
        if (this.ballImage.getBoundsInParent().intersects(myPaddle.getPaddleImage().getBoundsInParent())) {
            root.getChildren().remove(this.ballImage);
            initiatePowerUpEffects(this.powerUp, myPaddle, myBouncer);
            this.powerUp = NO_POWER;
        }
    }

    private void initiatePowerUpEffects(int powerUp, Paddle myPaddle, Ball myBouncer) {
        if (powerUp == SLOW_BOUNCER) {
            myBouncer.slowSpeed();
        } else if (powerUp == LONG_PADDLE) {
            myPaddle.increasePaddleWidth();
        } else if (powerUp == HEAVY_BALL) {
            myBouncer.setHeavy();
        } else if (powerUp == EXTRA_LIFE) {
            myBouncer.addLife();
        }
    }

    /**
     * Get the bouncer image.
     * @return bouncer image
     */
    public ImageView getPowerUpImage() {
        return this.ballImage;
    }

}
