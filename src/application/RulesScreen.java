package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Creates and displays the rules aka how to play window
 *
 * @author Jack
 *
 */
public class RulesScreen extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Text htp = new Text("                How to Play:"); // placed at top
		htp.setFont(new Font(75));

		// hold the rest of the text in one Text object with newline characters
		Text text = new Text("- To shoot the ball, click on the ball and move the"
				+ "\n    mouse in the opposite direction in which you"
				+ "\n    want the ball to move. Release the mouse button"
				+ "\n    once your shot is lined up to shoot the ball."
				+ "\n- Points are scored by pocketing the ball."
				+ "\n- Pocket the ball as many times as possible."
				+ "\n- When you are done playing, simply close the"
				+ "\n    window.");
		text.setFont(new Font(50));

		VBox vBox = new VBox(htp, text); // VBox to organize the header and the rest of text

		// create scene with the vBox in it, add it to the stage, and show the stage
		Scene scene = new Scene(vBox, 1152, 648);
		stage.setScene(scene);
		stage.show();
	}
}
