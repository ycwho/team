package application;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    private Client client;
    private Main main;
    @FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextArea infoBox;
	@FXML
	private Button logInButton;
	@FXML
	private Button registerButton;
	

	
	//todo work out host for client argument
    public LoginController() {
    }
    
    @FXML
	protected void login(MouseEvent event) throws IOException {
		if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
			String loginToServer = "log " + usernameField.getText() + " "+ passwordField.getText();
			System.out.println(loginToServer);
			this.client.login(loginToServer);
		} else {
			System.out.println("login failed");

		}
	}
    
    @FXML
   	protected void register(MouseEvent event) throws IOException {
    	if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
			String registerToServer = "register " + usernameField.getText() + " "+ passwordField.getText();
			System.out.println(registerToServer);
			this.client.register(registerToServer);
		} else {
			System.out.println("login failed");
		}
   	}

	@FXML
	protected void displayMessage(String message) {
		this.infoBox.setText(message);
		this.infoBox.setVisible(true);

		try { Thread.sleep(2000);}
		catch(InterruptedException ex)
		{ Thread.currentThread().interrupt(); }

		this.infoBox.setVisible(false);
	}
   
    public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

}
