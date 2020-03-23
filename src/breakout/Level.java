package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * Handles the loading and changing of levels.
 * Would fail if level goes above 5.
 * Depends on Main, Brick, Paddle, Ball, Power Up classes.
 * Uses java and javafx packages.
 */
public class Level {
    private static final int START_SCORE = 0;

    private int level;
    private int bricksToBreak;
    private int bricksBroken;
    private int score;
    private static final int SCORE_INCREMENT = 5;

    /**
     * Constructs a level object.
     * level and bricksToBreak are set to 0 by default.
     */
    public Level() { }

    /**
     * returns the current level of the user.
     * @return current level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Handles the changing of level, either by passing a level or a cheat code.
     * Clears the bricks on the current level and loads the bricks on the target level.
     * Resets the bouncer and the paddle.
     * At higher levels, the bouncer gets faster and the paddle gets smaller.
     * Fails if input is not a valid level.
     * @param brickList List that stores all bricks in old and new level
     * @param powerUpList List that stores all power ups in old and new level
     * @param root Group where nodes are added
     * @param myBouncer
     * @param myPaddle
     * @param resetLives boolean that determines if we reset lives on a level change
     * @param targetLevel level to change to
     */
    public void changeLevel(List<Brick> brickList, List<PowerUp> powerUpList, Group root, Ball myBouncer,
                            Paddle myPaddle, boolean resetLives, int targetLevel) {
        clearBricks(brickList, powerUpList, root);
        this.level = targetLevel;
        createBricks(brickList, powerUpList, root);
        myPaddle.resetPaddle(this.level);
        myBouncer.resetBouncer(myPaddle);
//        myBouncer.resetBricksBroken();
        this.bricksBroken = 0;
        if (resetLives) {
            myBouncer.resetLives();
            this.resetScore();
        }
    }

    /**
     * Responsible for tracking level progress.
     * Counts the bricks broken on the current level.
     * Accumulates score for the User, score increments are higher at higher levels.
     */
    public void accumulateLevelProgress() {
        this.bricksBroken += 1;
        this.score += SCORE_INCREMENT * this.level;
    }

    /**
     * Determines if the current game level is cleared.
     * @return true if the current level is passed
     */
    public boolean levelPassed() {
        return (this.bricksToBreak == this.bricksBroken) && this.isGameLevel();
    }

    /**
     * Determines if the current level is a game level.
     * @return true if the current level is a game level, false otherwise
     */
    public boolean isGameLevel() {
        return (this.level > Main.SPLASH_SCREEN && this.level < Main.WIN_SCREEN);
    }

    /**
     * Get the cumulative score of the player.
     * @return current score
     */
    public int getScore() { return this.score; }

    private void resetScore() { this.score = START_SCORE; }

    private void clearBricks(List<Brick> brickList, List<PowerUp> powerUpList, Group root) {
        for (Brick oldBrick : brickList) {
            root.getChildren().remove(oldBrick.getBrickImage());
        }
        for (PowerUp oldPowerUp : powerUpList) {
            root.getChildren().remove(oldPowerUp.getPowerUpImage());
        }
        brickList.clear();
        powerUpList.clear();
    }

    /**
     * Displays text on splash screen, win and lose screen.
     * @param splashScreen Splash Screen Text
     * @param win Win Screen Text
     * @param lose Lose Screen Text
     */
    public void displayText(Text splashScreen, Text win, Text lose) {
        splashScreen.setVisible(Main.NOT_VISIBLE);
        win.setVisible(Main.NOT_VISIBLE);
        lose.setVisible(Main.NOT_VISIBLE);
        if (this.level == Main.SPLASH_SCREEN) {
            splashScreen.setVisible(Main.VISIBLE);
        } else if (this.level == Main.WIN_SCREEN) {
            win.setVisible(Main.VISIBLE);
        } else if (this.level == Main.LOSE_SCREEN) {
            lose.setVisible(Main.VISIBLE);
        }
    }

    private void addBrick(List<Brick> brickList, List<PowerUp> powerUpList,
                          int lineNumber, int columnNumber, int brickType) {
        Brick newBrick = new Brick(brickType);
        double brickWidth = newBrick.getBrickImage().getFitWidth();
        double brickHeight = newBrick.getBrickImage().getFitHeight();
        newBrick.setBrickPosition(brickWidth * columnNumber, brickHeight * lineNumber);
        if (brickType == Brick.POWER_UP_BRICK) {
            Random random = new Random();
            int powerUpType = random.ints(PowerUp.MIN_POWER_UP, PowerUp.MAX_POWER_UP + 1).findFirst().getAsInt();
            PowerUp power = new PowerUp(powerUpType);
            //Decided to place the power up in the middle of the power up brick
            power.setPosition(brickWidth * columnNumber + brickWidth / 2, brickHeight * lineNumber);
            powerUpList.add(power);
        }
        brickList.add(newBrick);
        if (newBrick.givesPoints()) {
            this.bricksToBreak += 1;
        }
    }

    private boolean isValidBrick(int brickType) {
        return (brickType >= Brick.ONE_LIFE_BRICK && brickType <= Brick.DEATH_BRICK);
    }

    private void createBricks(List<Brick> brickList, List<PowerUp> powerUpList, Group root) {
        // referenced code from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
        File file = new File("././resources/level" + this.level + ".txt");
        int lineNumber = 0;
        try {
            Scanner sc = new Scanner(file);
            this.bricksToBreak = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (int i = 0; i < Math.round(Main.SIZE / Brick.BRICK_WIDTH); i++) {
                    char brick = line.charAt(i);
                    int brickType = Character.getNumericValue(brick);
                    if (isValidBrick(brickType)) {
                        addBrick(brickList, powerUpList, lineNumber, i, brickType);
                    }
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found!");
        }
        for (PowerUp powerUp : powerUpList) {
            root.getChildren().add(powerUp.getPowerUpImage());
        }
        for (Brick newBrick : brickList) {
            root.getChildren().add(newBrick.getBrickImage());
        }
    }

}
