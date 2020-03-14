package application;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.lang.model.type.PrimitiveType;

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
			String loginToServer = Protocol.CLIENT_LOGIN + " " + usernameField.getText() + " "+ passwordField.getText();
			System.out.println(loginToServer);
			this.client.write(loginToServer);
		} else if (usernameField.getText().isEmpty()){
			this.client.getLoginController().displayMessage("Missing Username");
			//System.out.println("login failed");
		}else if (passwordField.getText().isEmpty()){
			this.client.getLoginController().displayMessage("Missing Password");
			//System.out.println("login failed");
		}
	}
    
    @FXML
   	protected void register(MouseEvent event) throws IOException {
    	if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
    		if (usernameField.getText().indexOf(' ') < 0 && passwordField.getText().indexOf(' ') < 0){
				String registerToServer = Protocol.CLIENT_SIGNUP + " " + usernameField.getText() + " " + passwordField.getText();
				System.out.println(registerToServer);
				this.client.write(registerToServer);
			} else if (usernameField.getText().indexOf(' ') >= 0){
				this.client.getLoginController().displayMessage("Username must be one word");
			}else {
				this.client.getLoginController().displayMessage("Password must be one word");
			}
		} else if (usernameField.getText().isEmpty()){
			this.client.getLoginController().displayMessage("Missing Username");
			//System.out.println("login failed");
		}else if (passwordField.getText().isEmpty()){
			this.client.getLoginController().displayMessage("Missing Password");
			//System.out.println("login failed");
		}
   	}

	@FXML
	protected void displayMessage(String message) {
		this.infoBox.setText(message);
		this.infoBox.setVisible(true);

		try { Thread.sleep(1000);}
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
