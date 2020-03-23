package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A basic breakout game.
 * Depends on Ball, Brick, Level, Paddle, and Power Up classes.
 * Uses javafx and java.util packages.
 * Run the file and follow instructions on Splash Screen to play the game.
 *
 * @author Eric Tay, netid: et102
 */
public class Main extends Application {
    private static final String TITLE = "Example JavaFX";
    public static final int SIZE = 600;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int PADDLE_SPEED = 10;
    private static final boolean RESET_STATUS = true;
    private static final boolean NO_RESET_STATUS = false;
    public static final boolean VISIBLE = true;
    public static final boolean NOT_VISIBLE = false;
    public static final int SPLASH_SCREEN = 0;
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    public static final int WIN_SCREEN = 4;
    public static final int LOSE_SCREEN = 5;
    private static final int TOP_LEFT = 0;
    public static final int DISPLAY_HEIGHT = 20;
    public static final int RIGHT_WALL = SIZE;
    public static final int LEFT_WALL = 0;
    public static final int BOTTOM_WALL = SIZE;
    public static final int TOP_WALL = DISPLAY_HEIGHT;
    private static final int PADDLE_UPPER_LIMIT = (2 * SIZE) / 3;
    private static final int WIN_FONT_SIZE = 30;
    private static final int WIN_X_POSITION = 100;
    private static final int WIN_Y_POSITION = 280;
    private static final int DISPLAY_FONT_SIZE = 12;
    private static final int DISPLAY_Y_POSITION = 15;
    private static final int LIVES_X_POSITION = 10;
    private static final int SCORE_X_POSITION = 500;
    private static final int SPLASH_SCREEN_FONT_SIZE = 18;
    private static final int SPLASH_SCREEN_X_POSITION = 100;
    private static final int SPLASH_SCREEN_Y_POSITION = 130;
    private static final int NO_LIVES = 0;
    private static final String SPLASH_SCREEN_TEXT = "Press ENTER to play \n\n"
            + "Press SPACE to release the ball\n"
            + "Use arrow keys to move the paddle\n"
            + "You lose a life when the ball hits the bottom \n"
            + "Red bricks take one hit to destroy \n"
            + "Yellow bricks take two hits to destroy \n"
            + "Green bricks take three hits to destroy \n"
            + "Purple bricks drop a power-up \n"
            + "Grey and Black bricks are unbreakable \n"
            + "Black bricks make you lose a life when hit\n"
            + "Clear all the breakable bricks to pass the level\n"
            + "Your number of lives are on the top left\n"
            + "Your score is on the top right\n"
            + "Press I to return to the instructions page";
    private static final String WIN_TEXT = "You've Won!\n\n"
            + "Press ENTER to play again";
    private static final String LOSE_TEXT = "You've Lost!\n\n"
            + "Press ENTER to play again";

    private Scene myScene;
    private Ball myBouncer;
    private Paddle myPaddle;
    private List<Brick> brickList;
    private List<PowerUp> powerUpList;
    private Group root;
    private Text lives;
    private Text score;
    private Text splashScreen;
    private Text win;
    private Text lose;
    private Level myLevel;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start(Stage stage) {
        // Code was taken from lab code provided
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private Scene setupGame(int width, int height, Paint background) {
        // Code was adapted from lab code provided
        root = new Group();
        myPaddle = new Paddle();
        myPaddle.resetPaddle(LEVEL_1);
        myBouncer = new Ball();
        myBouncer.resetBouncer(myPaddle);
        brickList = new ArrayList<>();
        powerUpList = new ArrayList<>();
        myLevel = new Level();
        lives = formText("Lives: " + myBouncer.getLives(), LIVES_X_POSITION, DISPLAY_Y_POSITION,  DISPLAY_FONT_SIZE, Color.WHITE, VISIBLE);
        score = formText("Score: " + myLevel.getScore(), SCORE_X_POSITION, DISPLAY_Y_POSITION, DISPLAY_FONT_SIZE, Color.WHITE, VISIBLE);
        splashScreen = formText(SPLASH_SCREEN_TEXT, SPLASH_SCREEN_X_POSITION, SPLASH_SCREEN_Y_POSITION, SPLASH_SCREEN_FONT_SIZE, Color.BLACK, VISIBLE);
        win = formText(WIN_TEXT, WIN_X_POSITION, WIN_Y_POSITION, WIN_FONT_SIZE, Color.BLACK, NOT_VISIBLE);
        lose = formText(LOSE_TEXT, WIN_X_POSITION, WIN_Y_POSITION, WIN_FONT_SIZE, Color.BLACK, NOT_VISIBLE);
        Rectangle displayBackground = new Rectangle(TOP_LEFT, TOP_LEFT, SIZE, DISPLAY_HEIGHT);
        root.getChildren().addAll(myBouncer.getBallImage(), myPaddle.getPaddleImage(), displayBackground, lives, score, splashScreen, win, lose);
        StackPane.setAlignment(splashScreen, Pos.CENTER);
        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private Text formText(String text, double xPosition, double yPosition, int fontSize, Paint color, boolean visibility) {
        Text displayText = new Text();
        displayText.setText(text);
        displayText.setX(xPosition);
        displayText.setY(yPosition);
        displayText.setFont(Font.font("verdana", fontSize));
        displayText.setFill(color);
        displayText.setVisible(visibility);
        displayText.setTextAlignment(TextAlignment.CENTER);
        return displayText;
    }

    private void step(double elapsedTime) {
        myBouncer.bouncerMovement(elapsedTime, myPaddle, brickList, powerUpList, myLevel);
        lives.setText("Lives: " + myBouncer.getLives());
        score.setText("Score: " + myLevel.getScore());
        myLevel.displayText(splashScreen, win, lose);
        for (PowerUp powerUpBall : powerUpList) {
            powerUpBall.powerUpDrop(elapsedTime, myPaddle, root, myBouncer);
        }
        if (myLevel.levelPassed()) {
            myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, NO_RESET_STATUS, myLevel.getLevel() + 1);
        }
        if (myBouncer.getLives() == NO_LIVES) {
            myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, NO_RESET_STATUS, LOSE_SCREEN);
        }
        removeBricks(brickList);
    }

    private void removeBricks(List<Brick> brickList) {
        Iterator<Brick> itr = brickList.iterator();
        while (itr.hasNext()) {
            Brick myBrick = itr.next();
            if (myBrick.isDestroyed()) {
                root.getChildren().remove(myBrick.getBrickImage());
                itr.remove();
            }
        }
    }

    private void movePaddleWithBouncer(String direction, int speed) {
        myPaddle.move(direction, speed);
        if (myBouncer.isStationary()) {
            myBouncer.move(direction, speed);
        }
    }

    private void handleKeyInput(KeyCode code) {
        //decided to let non-'ENTER' keyboard inputs work only for game levels.
        if (myLevel.isGameLevel()) {
            // Designed such that the paddle cannot move out of screen, nor above a third of the screen
            if (code == KeyCode.RIGHT && (myPaddle.getPaddleImage().getX() + myPaddle.getPaddleImage().getFitWidth()
                    < RIGHT_WALL)) {
                movePaddleWithBouncer("right", PADDLE_SPEED);
            } else if (code == KeyCode.LEFT && (myPaddle.getPaddleImage().getX() > LEFT_WALL)) {
                movePaddleWithBouncer("left", PADDLE_SPEED);
            } else if (code == KeyCode.UP && (myPaddle.getPaddleImage().getY() > PADDLE_UPPER_LIMIT)) {
                movePaddleWithBouncer("up", PADDLE_SPEED);
            } else if (code == KeyCode.DOWN && (myPaddle.getPaddleImage().getY()
                    < BOTTOM_WALL - myPaddle.getPaddleImage().getFitHeight())) {
                movePaddleWithBouncer("down", PADDLE_SPEED);
            } else if (code == KeyCode.SPACE) {
                myBouncer.launchBouncer(myLevel.getLevel());
            } else if (code == KeyCode.DIGIT1) {
                myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, LEVEL_1);
            } else if (code == KeyCode.DIGIT2) {
                myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, LEVEL_2);
            } else if (code == KeyCode.DIGIT3) {
                myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, LEVEL_3);
            } else if (code == KeyCode.L) {
                myBouncer.addLife();
            } else if (code == KeyCode.R) {
                myPaddle.resetPaddle(myLevel.getLevel());
                myBouncer.resetBouncer(myPaddle);
            } else if (code == KeyCode.N) {
                myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, myLevel.getLevel() + 1);
            } else if (code == KeyCode.B) {
                myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, myLevel.getLevel() - 1);
            } else if (code == KeyCode.F) {
                myBouncer.increaseSpeed();
            } else if (code == KeyCode.S) {
                myBouncer.slowSpeed();
            } else if (code == KeyCode.E) {
                myPaddle.increasePaddleWidth();
            } else if (code == KeyCode.I) {
                myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, SPLASH_SCREEN);
            }
        } else if (code == KeyCode.ENTER) {
            myLevel.changeLevel(brickList, powerUpList, root, myBouncer, myPaddle, RESET_STATUS, LEVEL_1);
        }
    }

    /**
     * Start the program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
