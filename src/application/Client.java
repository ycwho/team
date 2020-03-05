package application;

// TODO gui crashing when closing the login gui

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
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

public class Client {

    private Socket server;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    //private BufferedReader fromUser;
    private Controller controller;
    private MainMenuController mainMenuController;
    private Main main;
    
    public void setMainMenuController(MainMenuController mainMenuController) {
		this.mainMenuController = mainMenuController;
	}

	private String[] input;
   
	public void setController(Controller controller) {
		this.controller = controller;
	}

	Client(String serverName, Main main) throws ClassNotFoundException {
        try {
        	this.main = main;
            server = new Socket(serverName, 50000);
            toServer = new ObjectOutputStream(server.getOutputStream());
            fromServer = new ObjectInputStream(server.getInputStream());
            toServer.flush();
            //fromUser = new BufferedReader(new InputStreamReader(System.in));
            String[] connected = {"Connected"};
            toServer.writeObject(connected); //server connected
            Thread fromServerThread = new Thread(new FromServer(fromServer, main));
			fromServerThread.start();
//            	 try {
//          		   input = (String[]) fromServer.readObject();
//          		   } catch (EOFException eof) {
//          			  String[] empty = {""};
//          			  input = empty;
//          		   }
//            	 if(input[0].equals("login")) {
//            		 if(input[1].equals("1"));
//            		 System.out.println("logged in");;
//            	 }
//            }
            
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
        } catch (IOException e) {
            System.out.println("I/O error");
        }
    }

    public ObjectOutputStream getToServer () {
        return toServer;
    }

    public ObjectInputStream getFromServer () {
        return fromServer;
    }
    
    public void login(String loginDetails) throws IOException {
    	toServer.writeObject(loginDetails.split(" "));
    }
    
    public void register(String loginDetails) throws IOException {
    	toServer.writeObject(loginDetails.split(" "));
    }

}

class FromServer implements Runnable {
	
	ObjectInputStream fromServer;
	String[] nextLine;
	Main main;
	
	public FromServer(ObjectInputStream fromServer, Main main) {
		this.fromServer = fromServer;
		this.main=main;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
				try {
					String[] nextLine = (String[]) fromServer.readObject();
					if(nextLine[0].equals("login")) {
						if(nextLine[1].equals("1")) {
							System.out.println("you are logged in");
							main.setMainMenuStage();
						}
					}
					if(nextLine[0].equals("register")) {
						if(nextLine[1].equals("1")) {
							System.out.println("you can now log in");
							main.setMainMenuStage();
						}
					}
					
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("From Server: " + Arrays.toString(nextLine));
		}
	}
}


//    //todo Finalise problems
//    @Override
//    public void finally () {
//        try {
//            // Let server know we are done.
//            // Our convention is to send "0" to indicate this.
//
//            toServer.writeInt(0);
//
//            toServer.close();
//            fromServer.close();
//            fromUser.close();
//
//            server.close();
//        }
//        catch (IOException e) {
//            System.err.println("Something went wrong ending the client");
//        }
//    }



    //    public static Group root = new Group();
//    Stage primaryStage;
//    static PrintWriter out;
//    static ObjectOutputStream toServer;
//    static ObjectInputStream fromServer;
//    //    private BufferedReader fromUser;
//
//    public static void main(String[] args) throws IOException {
//        if (args.length != 1) {
//            System.err.println("Pass the server IP as the sole command line argument");
//            return;
//        }
//        try (Socket server = new Socket(args[0], 50000)) {
//            while(true) {
//                toServer = new ObjectOutputStream(server.getOutputStream());
//                fromServer = new ObjectInputStream(server.getInputStream());
//                //fromUser = new BufferedReader(new InputStreamReader(System.in));
////                out = new PrintWriter(server.getOutputStream(), true);
////
////                out.println("con");
//                toServer.writeUTF("Connected"); //server connected
//                toServer.flush();
//                launch(args);
//            }
//
////                out.println(scanner.nextLine());
//
//        }
//
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // TODO Auto-generated method stub
//        primaryStage.setTitle("Battleships");
//
//        TextField usernameField = new TextField();
//        usernameField.setLayoutX(100);
//        usernameField.setLayoutY(100);
//        usernameField.setPromptText("Username");
//        PasswordField passwordField = new PasswordField();
//        passwordField.setPromptText("Password");
//        passwordField.setLayoutX(100);
//        passwordField.setLayoutY(150);
//
//        Button loginButton = new Button();
//        loginButton.setText("Login");
//        loginButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    toServer.writeUTF("login "+usernameField.getText()+" "+passwordField.getText());
//                    toServer.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        loginButton.setLayoutX(140);
//        loginButton.setLayoutY(200);
//
//        Button registerButton = new Button();
//        registerButton.setText("Register");
//        registerButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//
//                try {
//                    toServer.writeUTF("register "+usernameField.getText()+" "+passwordField.getText());
//                    toServer.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//        registerButton.setLayoutX(130);
//        registerButton.setLayoutY(250);
//
//        Pane root = new Pane();
//
//        root.getChildren().add(registerButton);
//        root.getChildren().add(loginButton);
//        root.getChildren().add(usernameField);
//        root.getChildren().add(passwordField);
//        primaryStage.setScene(new Scene(root, 300, 300));
//        primaryStage.show();
//    }
//
//}