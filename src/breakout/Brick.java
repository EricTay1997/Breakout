package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Creates different kinds of bricks.
 * Assumes that a valid brick type would be passed in (if not, brick is not created).
 * Depends on the Level and PowerUp class.
 * Uses javafx.scene.image packages.
 */
public class Brick {
    private ImageView brickImage;
    public static final double BRICK_WIDTH = 100;
    private static final double BRICK_HEIGHT = 20;
    public static final int ONE_LIFE_BRICK = 1;
    public static final int DEFAULT_BRICK = ONE_LIFE_BRICK;
    private static final int TWO_LIFE_BRICK = 2;
    private static final int THREE_LIFE_BRICK = 3;
    public static final int POWER_UP_BRICK = 4;
    private static final int UNBREAKABLE_BRICK = 5;
    public static final int DEATH_BRICK = 6;
    private static final int BRICK_LIFE_DECREASE = 1;

    private int brickType;
    private boolean toBeDestroyed;

    /**
     * Creates a brick of a certain type and fixed height and width.
     * @param type type of brick to be created. Currently 6 different specified types
     */
    public Brick(int type) {
        brickImage = new ImageView(
                new Image(getClass().getClassLoader().getResourceAsStream(chooseImageFromType(type))));
        brickType = type;
        brickImage.setFitHeight(BRICK_HEIGHT);
        brickImage.setFitWidth(BRICK_WIDTH);
    }

    /**
     * Changes the type of Multi-life bricks when they are hit by a normal bouncer.
     */
    public void changeType() {
        this.brickType -= BRICK_LIFE_DECREASE;
        this.brickImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream(chooseImageFromType(this.brickType))));
    }

    /**
     * Determines if a brick should be destroyed.
     * @return true if the brick should be destroyed in main, and false otherwise
     */
    public boolean isDestroyed() {
        return this.toBeDestroyed;
    }

    /**
     * Marks the brick as a brick that should be destroyed.
     */
    public void destroyBrick() {
        this.toBeDestroyed = true;
    }

    private String chooseImageFromType(int type) {
        if (type == ONE_LIFE_BRICK) {
            return "brick9.gif";
        } else if (type == TWO_LIFE_BRICK) {
            return "brick11.gif";
        } else if (type == THREE_LIFE_BRICK) {
            return "brick8.gif";
        } else if (type == POWER_UP_BRICK) {
            return "brick6.gif";
        } else if (type == UNBREAKABLE_BRICK) {
            return "brick3.gif";
        } else if (type == DEATH_BRICK) {
            return "brick12.gif";
        }
        return "";
    }

    /**
     * Get the brick image.
     * @return brick image
     */
    public ImageView getBrickImage() {
        return this.brickImage;
    }

    /**
     * Get the brick type.
     * @return brick type
     */
    public int getBrickType() {
        return this.brickType;
    }

    /**
     * Determines if a brick is breakable in one hit.
     * Only applies to non-heavy bouncer.
     * @return true if brick is breakable in one hit by a non-heavy bouncer
     */
    public boolean isBreakableInOneHit() {
        return (this.brickType == ONE_LIFE_BRICK || this.brickType == POWER_UP_BRICK);
    }

    /**
     * Determines if a brick is a two-life or three-life brick.
     * @return true if brick is a two-life or three-life brick
     */
    public boolean multipleLifeBrick() {
        return (this.brickType == TWO_LIFE_BRICK || this.brickType == THREE_LIFE_BRICK);
    }

    /**
     * Determines if a brick gives points.
     * These bricks all need to be broken to pass the current level.
     * @return true if the brick gives points
     */
    public boolean givesPoints() {
        return (isBreakableInOneHit() || multipleLifeBrick());
    }

    /**
     * Set the position of the brick.
     * Assumes that input is valid (on-screen)/accurate.
     * @param xPosition
     * @param yPosition
     */
    public void setBrickPosition(double xPosition, double yPosition) {
        this.brickImage.setX(xPosition);
        this.brickImage.setY(yPosition);
    }

    /**
     * Responsible for breaking a brick and associated effects like accumulating level progress and power up dropping.
     * @param powerUpList List of power ups on the level
     * @param myLevel Current Level object that determines level progress
     */
    public void breakBrick(List<PowerUp> powerUpList, Level myLevel) {
        if (this.brickType == Brick.POWER_UP_BRICK) {
            for (PowerUp powerUpBall : powerUpList) {
                if (powerUpBall.getPowerUpImage().getBoundsInParent().intersects(this.brickImage.getBoundsInParent())) {
                    powerUpBall.initiatePowerUpDrop();
                }
            }
        }
        if (this.givesPoints()) {
            myLevel.accumulateLevelProgress();
        }
        this.destroyBrick();
    }

    /**
     * Responsible for the breaking, or the changing of a brick's image when hit.
     * @param powerUpList List of power ups on the level
     * @param myLevel Current Level object that determines level progress
     */
    public void changeBrickImage(List<PowerUp> powerUpList, Level myLevel) {
        if (this.isBreakableInOneHit()) {
            breakBrick(powerUpList, myLevel);
        } else if (this.multipleLifeBrick()) {
            this.changeType();
        }
    }

}
