package copyOfHenryApplication;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {

    private Client client;
    private Mainhenry main;
    @FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button logInButton;
	@FXML
	private Button registerButton;
	

	
	//todo work out host for client argument
    public Controller() {
    }
    
    @FXML
	protected void login(MouseEvent event) throws IOException {
		if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
			String loginToServer = "login " + usernameField.getText() + " "+ passwordField.getText();
			this.client.login(loginToServer);
		} else {
			System.out.println("login failed");

		}
	}
    
    @FXML
   	protected void register(MouseEvent event) throws IOException {
    	System.out.println("pressed");
   		if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
   			String registerToServer = "register " + usernameField.getText() + " "+ passwordField.getText();
   			this.client.register(registerToServer);
   		} else {
   			System.out.println("login failed");

   		}
   	}
   
    public Mainhenry getMain() {
		return main;
	}
	public void setMain(Mainhenry main) {
		this.main = main;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

}
