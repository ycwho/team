package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
//    	System.out.println("pressed");
//   		if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
//   			String registerToServer = "register " + usernameField.getText() + " "+ passwordField.getText();
//   			this.client.register(registerToServer);
//   		} else {
//   			System.out.println("login failed");
//
//   		}
    	this.client.register("REG PRESSED");
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
