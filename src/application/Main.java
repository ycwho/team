package application;

import java.io.IOException;
import GUI.GUIMain;
import GUI.PregameSetUp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    	client = new Client("localhost", this);
    	setLoginStage();
    	
    	primaryStage.setOnCloseRequest(event -> {
			System.out.println("Stage is closing");
			System.exit(0);
		});
    	
    	
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	public void setLoginStage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginFX.fxml"));
	    Parent root = loader.load();
	    LoginController loginController = loader.getController();
	    loginController.setClient(client);
	    loginController.setMain(this);
	    this.client.setLoginController(loginController);
	    Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Asos Battleships");
		primaryStage.show();
	}
	
	public void setGameLobbyStage() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameLobbyFX.fxml"));
		Parent root = fxmlLoader.load();
		GameLobbyController controller = fxmlLoader.getController();
		controller.setClient(client);
		controller.setMain(this);
		this.client.setGameLobbyController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Game Lobby");
		primaryStage.show();
	}
	
	public void setGameLobbySetupStage() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shipSetupLobby.fxml"));
		Parent root = fxmlLoader.load();
		ShipSetupLobbyController controller = fxmlLoader.getController();
		controller.setClient(client);
		controller.setMain(this);
		this.client.setShipSetupLobbyController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Game Lobby Ship Setup");
		primaryStage.show();
	}
	
	public void setSinglePlayerGame() throws Exception {
		Stage singleStage = new Stage();
		GUIMain guimain = new GUIMain();
		GUI.GUIMain.setUp();
		guimain.start(singleStage);
	}

	public void setSetup() throws Exception {
//		Stage singleStage = new Stage();
//		PregameSetUp pregameSetUp = new PregameSetUp(this.client);
//		pregameSetUp.setUp();
//		pregameSetUp.start(singleStage);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shipSetupFX.fxml"));
		Parent root = fxmlLoader.load();
		ShipSetupController controller = fxmlLoader.getController();
		controller.setClient(client);
		controller.setMain(this);
		this.client.setShipSetupController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Ship Setup");
		primaryStage.show();
	}
	
	public void setMainMenuStage() throws Exception {
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
		primaryStage.setTitle("Battleships Main Menu");
		primaryStage.show();

	}
	//arg String list of players separated by spaces
	public void setGameStage(String string) throws Exception { 
		String[] players = string.split(" ");
		GUI2.Main m = new GUI2.Main(players);
		m.start(new Stage());
	}
//	public void setGameStage(Stage primaryStage) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("gameFX.fxml"));
//		Parent root = loader.load();
//		GameController controller = loader.getController();
//		controller.setClient(client);
//		controller.setMain(this);
//		this.client.setGameController(controller);
//		Scene scene = new Scene(root);
//		primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
//		primaryStage.sizeToScene();
//		primaryStage.setTitle("Battleships");
//		primaryStage.show();
//	}
	
	
}