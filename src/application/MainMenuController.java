package application;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuController {
	
	ObservableList<String> playerNumber = FXCollections.observableArrayList("2","3","4");
	private Client client;
	private Main main;
//	public Stage primaryStage;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private TextField gameNameField;
	@FXML
	private ImageView checkUsersIcon;
	@FXML
	private Button createGame;
	@FXML
	private Button joinGame;
	@FXML
	private Button gamesListButton;
	@FXML
	private Button backButton;
	@FXML
	private Button playSinglePlayer;
	@FXML
	private TextArea usersList;
	@FXML
	private TextArea infoBox;
	@FXML
	private TextArea gamesList;
	private boolean userPressed = false;
	private boolean gamesPressed = false;

//	public void setPrimaryStage(Stage primaryStage) {
//			this.primaryStage = primaryStage;
//	}

	//todo work out host for client argument
	public MainMenuController() {
		
	}
	
	public void setTextArea(String text) {
		this.usersList.setText(text);
	}
	
	public void setGamesListTextArea(String text) {
		this.gamesList.setText(text);
	}
	
	@FXML
	protected void changeToSinglePlayer(MouseEvent event) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					main.setSinglePlayerGame();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@FXML
	protected void toLoginScreen(MouseEvent event) throws IOException {
		this.client.logout();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					main.setLoginStage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("error switchign stages");
				}
			}
		});
	}
	
	@FXML
	protected void listGames(MouseEvent event) throws IOException {
		if(!gamesPressed) {
		String checkServer = Protocol.CLIENT_CHECK_GAME;
		//System.out.println(checkToServer);
		this.gamesList.setVisible(true);
		this.client.checkGames(checkServer);
		gamesPressed = true;
		}
		else {
			this.gamesList.setVisible(false);
			gamesPressed = false;
		}
	}
	
	@FXML
	protected void checkOnline(MouseEvent event) throws IOException {
		if(!userPressed) {
		String checkServer = Protocol.CLIENT_CHECK_ONLINE_USER;
		//System.out.println(checkToServer);
		this.usersList.setVisible(true);
		this.client.checkOnline(checkServer);
		userPressed = true;
		}
		else {
			this.usersList.setVisible(false);
			userPressed = false;
		}
	}

	@FXML
	protected void displayMessage(String message) {
		this.infoBox.setText(message);
		this.infoBox.setVisible(true);

		try {TimeUnit.SECONDS.sleep(2);}
		catch(InterruptedException ex)
		{ Thread.currentThread().interrupt(); }

		this.infoBox.setVisible(false);
	}

	 @FXML
	 private void initialize() {
		 comboBox.getItems().setAll(playerNumber);
	 }

	@FXML
	protected void createGame(MouseEvent event) throws IOException {
		if (!gameNameField.getText().isEmpty()) {
		String createGameToServer = Protocol.CLIENT_CREATE_GAME + " " + comboBox.getValue() + " " + gameNameField.getText();
			System.out.println(createGameToServer);
			this.client.createGame(createGameToServer);
		} else {
			System.out.println("Game Creation Failed");
		}
	}

	@FXML
	protected void joinGame(MouseEvent event) throws IOException {
		String joinRequest = Protocol.CLIENT_JOIN_GAME + " " + gameNameField.getText();
		//System.out.println(joinRequest);
		this.client.joinGame(joinRequest);
	}


	    @FXML
		protected void users(MouseEvent event) throws IOException {
			System.out.println("pressed");
		}

	public Client getClient() {
			return client;
		}
	public void setClient(Client client) {
			this.client = client;
		}
	public Main getMain() {
			return main;
		}
	public void setMain(Main main) {
			this.main = main;
		}



}
