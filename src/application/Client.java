package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Client extends Application{

	public static Group root = new Group();
	Stage primaryStage;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
		try (Socket socket = new Socket(args[0], 50000)) {
          while(true) {
        	  out = new PrintWriter(socket.getOutputStream(), true);
        	  out.println("Client Connected");
        	  launch(args);
          }
   
//                out.println(scanner.nextLine());
            
        }
		
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Battleships");
		 
		 TextField usernameField = new TextField();
		 usernameField.setLayoutX(100);
		 usernameField.setLayoutY(100);
		 usernameField.setPromptText("Username");
		 PasswordField passwordField = new PasswordField();
		 passwordField.setPromptText("Password");
		 passwordField.setLayoutX(100);
		 passwordField.setLayoutY(150);
		 
		 
		 
		    Button loginButton = new Button();
		    loginButton.setText("Login");
		    loginButton.setOnAction(new EventHandler<ActionEvent>() {

		        @Override
		        public void handle(ActionEvent event) {
		            out.println("login "+usernameField.getText()+" "+passwordField.getText()); // sends to server in form: login username password    separated by spaces.
		        }
		    });
		    loginButton.setLayoutX(140);
		    loginButton.setLayoutY(200);
		    
		    Button registerButton = new Button();
		    registerButton.setText("Register");
		    registerButton.setOnAction(new EventHandler<ActionEvent>() {

		        @Override
		        public void handle(ActionEvent event) {
		            out.println("register "+usernameField.getText()+" "+passwordField.getText());
		            //send to client()
		            // gets response if successful
		            // if successful launch game screen
		        }
		    });
		    registerButton.setLayoutX(130);
		    registerButton.setLayoutY(250);

		    Pane root = new Pane();
	
		    
		    root.getChildren().add(registerButton);
		    root.getChildren().add(loginButton);
		    root.getChildren().add(usernameField);
		    root.getChildren().add(passwordField);
		    primaryStage.setScene(new Scene(root, 300, 300));
		    primaryStage.show();
	}	
}