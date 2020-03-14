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
	private Button setupButton;
	@FXML
	private Button loadShipsButton;
	@FXML
	private ImageView image;
	@FXML
	private Text text;
	
	@FXML
	protected void setupShips(MouseEvent event) throws IOException {
		try {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						main.setSetup();
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
	
	@FXML
	protected void loadShips(MouseEvent event) throws IOException {
		try {
			main.setSetup();
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
