package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * This is the main menu window that appears when the application is first launched.
 * Allows the user to start a game, view rules, or exit the application with the 3
 * available menu buttons.
 *
 * @author Jack Bragg
 *
 */
public class MainMenu extends Application {
	@Override
	public void start(Stage stage) {
		// create buttons for main menu
        Button playButton = new Button("Start Game");
        playButton.setPrefSize(500, 50);
        playButton.setStyle("-fx-font-size:40");

        Button ruleButton = new Button("How to Play");
        ruleButton.setPrefSize(500, 50);
        ruleButton.setStyle("-fx-font-size:40");
        ruleButton.setOnAction(event -> {
        	RulesScreen howToPlay = new RulesScreen();
        	try {
				howToPlay.start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setPrefSize(500, 50);
        exitButton.setStyle("-fx-font-size:40");
        exitButton.setOnAction(event -> {
        	System.exit(0);
        });

        // create a grid to set the buttons in the center of the app
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.add(playButton, 0, 0);
        grid.add(ruleButton, 0, 1);
        grid.add(exitButton, 0, 2);

        // display the app
        Scene scene = new Scene(grid, 1152, 648);
        stage.setScene(scene);
        stage.show();

        // if play button is clicked -> launch game window and hide main menu
        playButton.setOnAction(event -> {
        	GameScreen game = new GameScreen();
        	try {
				game.start(new Stage());
				stage.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
	}

	/**
	 * This main just launches the application.
	 * @param args does not hold anything
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
