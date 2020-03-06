package copyOfHenryApplication;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuController {

	 private Client client;
	    private Mainhenry main;
	    public Stage primaryStage;
	    
		public void setPrimaryStage(Stage primaryStage) {
			this.primaryStage = primaryStage;
		}

		@FXML
		private RadioButton usersButton;
		
		//todo work out host for client argument
	    public MainMenuController() {
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

		public Mainhenry getMain() {
			return main;
		}

		public void setMain(Mainhenry main) {
			this.main = main;
		}


	    
}
