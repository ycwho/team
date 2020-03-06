package merge;

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
	AnchorPane rootPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
//        primaryStage.setTitle("BattleShips");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    	client = new Client("localhost", this);
    	setLoginStage(primaryStage);
    	
    	
    	
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	public void setLoginStage(Stage primaryStage) throws IOException {
		 FXMLLoader loader = new FXMLLoader();
	        URL url = new File("res/loginFX.fxml").toURI().toURL();
	        loader.setLocation(url);
	        VBox vbox = loader.<VBox>load();
	        Scene scene = new Scene(vbox);
	        primaryStage.setScene(scene);
	        primaryStage.getIcons().add(new Image("icon.png"));
	        
		Controller controller = loader.getController();
		controller.setClient(client);
		controller.setMain(this);// pointing to this instance
		this.client.setController(controller);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Battleships Asos");


	        primaryStage.show(); 

	}
	
	public void setMainMenuStage() throws IOException {
		 FXMLLoader loader = new FXMLLoader();
	        URL url = new File("res/mainMenuFX.fxml").toURI().toURL();
	        loader.setLocation(url);
	        VBox vbox = loader.<VBox>load();
	        Scene scene2 = new Scene(vbox);
	        primaryStage.setScene(scene2);
	        primaryStage.getIcons().add(new Image("icon.png"));
	        
		Controller controller = loader.getController();
		controller.setClient(client);
		controller.setMain(this);// pointing to this instance
		this.client.setController(controller);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Battleships Asos");


	        primaryStage.show(); 
		System.out.println("to main menu");
	        rootPane.getChildren().setAll(pane);

	}
	
	
}