package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * MainMenu class for the Main Menu where the game is accessed from
 *
 */

public class MainMenu extends Application {
	@Override
	public void start(Stage stage) {
		// create buttons for main menu
        Button playButton = new Button("Start Game");
        playButton.setPrefSize(500, 50);
        playButton.setStyle("-fx-font-size:40");
        Button ruleButton = new Button("Rules");
        ruleButton.setPrefSize(500, 50);
        ruleButton.setStyle("-fx-font-size:40");
        Button quitButton = new Button("Exit Game");
        quitButton.setPrefSize(500, 50);
        quitButton.setStyle("-fx-font-size:40");

        // create a grid to set the buttons in the center of the app
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.add(playButton, 0, 0);
        grid.add(ruleButton, 0, 1);
        grid.add(quitButton, 0, 2);

        // display the app
        Scene scene = new Scene(grid, 1152, 648);
        stage.setScene(scene);
        stage.show();

        // if play button is clicked -> launch game window
        playButton.setOnAction(event -> {
        	GameScreen game = new GameScreen();
        	try {
				game.start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
	}

	public static void main(String[] args) {
		launch(args);
	}
}
