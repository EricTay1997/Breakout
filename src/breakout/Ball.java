package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.lang.Math;

/**
 * Creates the bouncer.
 * Alters bouncer position, direction, speed, lives and heaviness.
 * Responsible for bouncer movement and consequent effects.
 * Returns the image and lives of the bouncer
 * Depends on Main, Brick, Paddle, PowerUp and Level classes.
 * Uses javafx.scene.image and java packages.
 *
 * I feel that this class is well designed because there is no duplicated code, all methods are short and well-named,
 * and there are no magic values, which helps the code be readable. I feel that this class also has a very specific
 * purpose - to handle to bouncer, which is why I created a new Power Up class, but also has significant functionality
 * and does not just store data. Professor Duvall also mentioned how we should not pass things like the root around,
 * so I did not just move Brick-related code to the Brick class, but also managed to redesign the code that handled the
 * breaking of bricks such that the root was not passed into the Brick class. I also feel that the implementation of
 * this class is also private as mentioned. I chose this class in particular because I felt it was the hardest class
 * for me to implement and would like to find out what misconceptions I have about good design.
 */
public class Ball {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private static final double PADDLE_EXTREME_LEFT = 1.0 / 6;
    private static final double PADDLE_LEFT = 1.0 / 3;
    private static final double PADDLE_RIGHT = 2.0 / 3;
    private static final double PADDLE_EXTREME_RIGHT = 5.0 / 6;
    private static final int LIFE_DECREMENT = 1;
    private static final int LIFE_INCREMENT = 1;
    private static final int BOUNCER_SPEED = 150;
    private static final double STATIONARY = 0.0;
    private static final double UP = -1.0;
    private static final double LEFT = -1.0;
    private static final double RIGHT = 1.0;
    private static final int REVERSE = -1;
    private static final double SLOW_RIGHT = 0.5;
    private static final double FAST_RIGHT = 1.0;
    private static final double SLOW_LEFT = -0.5;
    private static final double FAST_LEFT = -1.0;
    private static final double SLOW_SPEED = 0.5;
    private static final double GET_FASTER = 1.2;
    private static final int START_LIVES = 3;

    private double xDirection;
    private double yDirection;
    private double ballSpeed;
    private ImageView ballImage;
    private boolean heavy;
    private int lives;

    /**
     * Creates a bouncer with a specified number of lives
     *
     */
    public Ball() {
        ballImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE)));
        lives = START_LIVES;
    }

    /**
     * Power up effect - changes the bouncer to be a heavy bouncer.
     */
    public void setHeavy() {
        this.heavy = true;
    }

    /**
     * Get the bouncer image.
     * @return bouncer image
     */
    public ImageView getBallImage() {
        return this.ballImage;
    }

    /**
     * Get the number of lives of the user.
     * @return number of lives
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Power up effect - slows down bouncer.
     */
    public void slowSpeed() {
        this.ballSpeed *= SLOW_SPEED;
    }

    /**
     * Power up effect - speeds up bouncer.
     */
    public void increaseSpeed() {
        this.ballSpeed *= GET_FASTER;
    }

    /**
     * Causes user to lose a life.
     */
    public void loseLife() {
        this.lives -= LIFE_DECREMENT;
    }

    /**
     * Power up effect - adds a life to the user.
     */
    public void addLife() {
        this.lives += LIFE_INCREMENT;
    }

    /**
     * Resets the number of lives of the user to a default value.
     */
    public void resetLives() {
        this.lives = START_LIVES;
    }

    /**
     * Determines if a bouncer is stationary.
     * @return true if bouncer is stationary, false otherwise
     */
    public boolean isStationary() { return (this.ballSpeed == STATIONARY); }

    private boolean isMovingRight() { return (this.xDirection > 0); }

    private boolean isMovingDown() { return (this.yDirection > 0); }

    private boolean bounceRightWall() { return this.ballImage.getBoundsInParent().getMaxX() >= Main.RIGHT_WALL && this.isMovingRight(); }

    private boolean bounceLeftWall() { return this.ballImage.getX() <= Main.LEFT_WALL && !(this.isMovingRight()); }

    private boolean bounceTopWall() { return this.ballImage.getY() <= Main.TOP_WALL && !(this.isMovingDown()); }

    private boolean bounceBottomWall() { return this.ballImage.getBoundsInParent().getMaxY() >= Main.BOTTOM_WALL; }

    private void bounceWall(double elapsedTime, Paddle myPaddle, int level) {
        this.ballImage.setX(this.ballImage.getX() + this.ballSpeed * this.xDirection * elapsedTime);
        this.ballImage.setY(this.ballImage.getY() + this.ballSpeed * this.yDirection * elapsedTime);
        if (bounceRightWall() || bounceLeftWall()) {
            this.xDirection *= REVERSE;
        }
        if (bounceTopWall()) {
            this.yDirection *= REVERSE;
        } else if (bounceBottomWall()) {
            this.triggerDeath(myPaddle, level);
        }
    }

    private void triggerDeath(Paddle myPaddle, int level) {
        //Design decision to make player lose power ups after death
        this.loseLife();
        myPaddle.resetPaddle(level);
        resetBouncer(myPaddle);
    }

    /**
     * Reset bouncer to be stationary, and upward y-direction.
     * Reset bouncer position to rest above the center of the paddle.
     * @param myPaddle
     */
    public void resetBouncer(Paddle myPaddle) {
        this.ballSpeed = STATIONARY;
        this.heavy = false;
        this.xDirection = STATIONARY;
        this.yDirection = UP;
        double xCenterOfPaddle = myPaddle.getPaddleImage().getX() + myPaddle.getPaddleImage().getFitWidth() / 2;
        double yTopOfPaddle = myPaddle.getPaddleImage().getY();
        this.ballImage.setX(xCenterOfPaddle - this.ballImage.getBoundsInLocal().getWidth() / 2);
        this.ballImage.setY(yTopOfPaddle - this.ballImage.getBoundsInLocal().getHeight());
    }

    /**
     * Handles launching the ball from the paddle.
     * Triggered on space-bar.
     * @param level current game level; the higher the level, the faster the bouncer speed
     */
    public void launchBouncer(int level) {
        this.ballSpeed = BOUNCER_SPEED * Math.pow(GET_FASTER, level);
    }

    private void paddleBounce(Paddle myPaddle) {
        double bouncerXCenter = this.ballImage.getX() + this.ballImage.getBoundsInLocal().getWidth() / 2;
        double paddleXPosition = myPaddle.getPaddleImage().getX();
        double paddleWidth = myPaddle.getPaddleImage().getFitWidth();
        double bouncerPositionOnPaddle = bouncerXCenter - paddleXPosition;
        if (this.isMovingDown()) {
            this.yDirection *= REVERSE;
        }
        if (bouncerPositionOnPaddle < paddleWidth * PADDLE_EXTREME_LEFT) {
            this.xDirection = FAST_LEFT;
        } else if (bouncerPositionOnPaddle < paddleWidth * PADDLE_LEFT) {
            this.xDirection = SLOW_LEFT;
        } else if (bouncerPositionOnPaddle > paddleWidth * PADDLE_EXTREME_RIGHT) {
            this.xDirection = FAST_RIGHT;
        } else if (bouncerPositionOnPaddle > paddleWidth * PADDLE_RIGHT) {
            this.xDirection = SLOW_RIGHT;
        }
    }

    private void brickBounce(Brick myBrick) {
        if (this.bounceOffBrickSide(myBrick)) {
            this.xDirection *= REVERSE;
        } else {
            this.yDirection *= REVERSE;
        }
    }

    private boolean bounceOffBrickSide(Brick myBrick) {
        double bouncerWidth = this.ballImage.getBoundsInLocal().getWidth();
        double bouncerLeft = this.ballImage.getX();
        double bouncerRight = bouncerLeft + bouncerWidth;
        double brickLeft = myBrick.getBrickImage().getX();
        double brickRight = brickLeft + myBrick.getBrickImage().getFitWidth();
        return (this.xDirection == RIGHT && bouncerLeft <= brickLeft)
                || (this.xDirection == LEFT && bouncerRight >= brickRight);
    }

    /**
     * Handles the movement of the bouncer.
     * Handles bouncer interaction with the walls, paddle and bricks.
     * @param elapsedTime
     * @param myPaddle
     * @param brickList List of all bricks in the game
     * @param powerUpList List of all power ups in the game
     * @param myLevel Level object, which is used to accumulate game progress
     */
    public void bouncerMovement(double elapsedTime, Paddle myPaddle, List<Brick> brickList,
                                List<PowerUp> powerUpList, Level myLevel) {
        Brick collidingBrick = new Brick(Brick.DEFAULT_BRICK);
        boolean brickCollide = false;
        bounceWall(elapsedTime, myPaddle, myLevel.getLevel());
        if (bouncePaddle(myPaddle)) {
            paddleBounce(myPaddle);
        }
        for (Brick oneBrick : brickList) {
            if (bounceBrick(oneBrick)) {
                collidingBrick = oneBrick;
                brickCollide = true;
            }
        }
        if (brickCollide) {
            brickCollision(collidingBrick, powerUpList, myLevel, this.heavy);
        }
    }

    private boolean bouncePaddle(Paddle myPaddle) {
        return this.ballImage.getBoundsInParent().intersects(myPaddle.getPaddleImage().getBoundsInParent())
                && !(this.isStationary());
    }

    private boolean bounceBrick(Brick oneBrick) {
        return this.ballImage.getBoundsInParent().intersects(oneBrick.getBrickImage().getBoundsInParent());
    }

    private void brickCollision(Brick collidingBrick, List<PowerUp> powerUpList, Level myLevel, boolean isBouncerHeavy) {
        if (collidingBrick.getBrickType() == Brick.DEATH_BRICK) {
            this.loseLife();
        }
        if (isBouncerHeavy) {
            // Decided to let heavy balls break all bricks, and have its motion unimpeded on contact
            collidingBrick.breakBrick(powerUpList, myLevel);
        } else {
            brickBounce(collidingBrick);
            collidingBrick.changeBrickImage(powerUpList, myLevel);
        }
    }

    /**
     * Handles movement of the bouncer on the paddle before bouncer is launched.
     * Movement is triggered by keyboard input
     * @param direction direction that the bouncer moves
     * @param speed increment in specified direction
     */
    public void move(String direction, int speed) {
        if (direction == "left") {
            this.ballImage.setX(this.ballImage.getX() - speed);
        } else if (direction == "right") {
            this.ballImage.setX(this.ballImage.getX() + speed);
        } else if (direction == "up") {
            this.ballImage.setY(this.ballImage.getY() - speed);
        } else if (direction == "down") {
            this.ballImage.setY(this.ballImage.getY() + speed);
        }
    }
}
