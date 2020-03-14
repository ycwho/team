package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class GameLobbyController {
	private Client client;
    private Main main;
	@FXML
	private ImageView image;
	@FXML
	private Text text;
	@FXML
	private TextField loadText;
	@FXML
	private Button setupButton;
	@FXML
	private Button loadShipsButton;
	@FXML
	private Button startButton;

	@FXML
	protected void setupShips(MouseEvent event) throws IOException {
		try {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						main.setGameLobbySetupStage();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//test button
	@FXML
	protected void start(MouseEvent event) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					main.setGameStage("Henry, Liz, Henry2");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@FXML
	protected void loadShips(MouseEvent event) throws IOException {
		try {
			//write protocol load ships
//			loadText.getText()
			client.write(Protocol.CLIENT_LOAD_POSITIONS_REQUEST + " " + this.client.getUsername() + " " + loadText.getText());
			System.out.println(Protocol.CLIENT_LOAD_POSITIONS_REQUEST + " " + this.client.getUsername() + " " + loadText.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
