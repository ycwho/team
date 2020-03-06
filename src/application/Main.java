package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	Stage primaryStage;
	Client client;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
//        primaryStage.setTitle("BattleShips");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    	this.primaryStage = primaryStage;
    	setLoginStage(primaryStage);
    	client = new Client("localhost", this);
    	client.run();
    	
    	primaryStage.setOnCloseRequest(event -> {
			System.out.println("Stage is closing");
			System.exit(0);
		});
    	
    	
    	
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	public void setLoginStage(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginFX.fxml"));
	    Parent root = loader.load();
	    Controller controller = loader.getController();
	    controller.setClient(client);
	    controller.setMain(this);
	    this.client.setController(controller);
	    Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Asos Battleships");
		primaryStage.show();
	}
	
	public void setMainMenuStage() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenuFX.fxml"));
		Parent root = fxmlLoader.load();
		MainMenuController controller = fxmlLoader.getController();
		controller.setClient(client);
		controller.setMain(this);
		this.client.setMainMenuController(controller);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Battleships Register User");
		primaryStage.show();

	}
	
	
}