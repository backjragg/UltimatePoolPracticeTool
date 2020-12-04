package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Game Screen class creates and hosts the game of pool.
 *
 * @author Jack Bragg
 *
 */
public class GameScreen extends Application {

	private static int score = 0; // how many times the ball was pocketed
	private static Text scoreDisplay;

	/**
	 * start will launch the window.
	 * @param stage holds everything you see in the window
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// outer table is the wooden looking part
		Rectangle outerTable = new Rectangle();
		outerTable.setX(230.4);
		outerTable.setY(129.6);
		outerTable.setWidth(691.2);
		outerTable.setHeight(388.8);
		outerTable.setArcWidth(40.0);
		outerTable.setArcHeight(40.0);
		outerTable.setFill(Color.SADDLEBROWN);

		// creates the inner table where balls will roll around
		Rectangle innerTable = new Rectangle();
		innerTable.setX(264.96);
		innerTable.setY(164.16);
		innerTable.setWidth(622.08);
		innerTable.setHeight(319.68);
		innerTable.setFill(Color.GREEN);

		// top left pocket
		Circle topLeftP = new Circle(265, 164, 18);

		// bottom left pocket
		Circle bottomLeftP = new Circle(265, 483.68, 18);

		//top right pocket
		Circle topRightP = new Circle(888, 164, 18);

		// bottom right pocket
		Circle bottomRightP = new Circle(888, 483.68, 18);

		// top middle pocket
		Circle topMidP = new Circle(576.5, 160, 18);

		// bottom middle pocket
		Circle bottomMidP = new Circle(576.5, 487.68, 18);

		// make the cue ball
		CueBall makeCueBall = new CueBall(420.75, 321.84, Color.WHITE);
		Circle cueBall = makeCueBall.getBall();

		Text scoreText = new Text(825, 75, "Score:"); // just displays "Score:" next to the score
		scoreText.setFont(new Font(50));
		scoreDisplay = new Text(1000, 75, String.valueOf(score)); // will display the score
		scoreDisplay.setFont(new Font(50));

		// create the group object which holds all the objects of the scene
		Group group = new Group(outerTable, innerTable, topLeftP, bottomLeftP, topRightP, bottomRightP, topMidP, bottomMidP, cueBall, makeCueBall.getDirectionLine(), scoreText, scoreDisplay);
		Scene scene = new Scene(group, 1152, 648, Color.BEIGE);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * to allow pocketing of cue ball to change the score
	 * @param score the number to update score to
	 */
	public static void setScore(int score) {
		GameScreen.score = score;
		setScoreDisplay(score);
	}

	/**
	 * to allow the cue ball to access the score in order to change it
	 * @return the current score
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * Change the displayed score to current score
	 * @param score the current score
	 */
	public static void setScoreDisplay(int score) {
		scoreDisplay.setText(String.valueOf(score));
	}
}
