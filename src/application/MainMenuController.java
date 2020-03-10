package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuController {

	private Client client;
	private Main main;
//	public Stage primaryStage;
	@FXML
	private TextField gameNameField;
	@FXML
	private ImageView checkUsersIcon;
	@FXML
	private Button createGame;
	@FXML
	private Button joinGame;
	@FXML
	private TextArea usersList;
	private boolean userPressed = false;

//	public void setPrimaryStage(Stage primaryStage) {
//			this.primaryStage = primaryStage;
//	}

	//todo work out host for client argument
	public MainMenuController() {
	}
	
	public void setTextArea(String text) {
		this.usersList.setText(text);
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
	protected void createGame(MouseEvent event) throws IOException {

		if (!gameNameField.getText().isEmpty()) {
		String createGameToServer = Protocol.CLIENT_CREATE_GAME + " " + gameNameField.getText();
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
