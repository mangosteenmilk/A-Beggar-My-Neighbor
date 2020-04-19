package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DrawCards.fxml"));					//load the GUI in 
		Scene scene = new Scene(root, 650, 500);													//create scene
		stage.setTitle("Draw Cards");																//set title
		stage.setScene(scene);																		//set the scene onto the stage
		scene.getStylesheets().add(getClass().getResource("DrawCardsStyle.css").toExternalForm());	//load the .css file
		stage.setResizable(false);
		stage.show();																				//show the application
				
	}
	
}
