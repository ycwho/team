package application;

// TODO gui crashing when closing the login gui
// todo tried to write Client object, but failed so converted back to just a main (left other code in comments)

import java.io.*;
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
    static ObjectOutputStream toServer;
    static ObjectInputStream fromServer;
    //    private BufferedReader fromUser;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        try (Socket server = new Socket(args[0], 50000)) {
            while(true) {
                toServer = new ObjectOutputStream(server.getOutputStream());
                fromServer = new ObjectInputStream(server.getInputStream());
                //fromUser = new BufferedReader(new InputStreamReader(System.in));
//                out = new PrintWriter(server.getOutputStream(), true);
//
//                out.println("con");
                toServer.writeUTF("Connected"); //server connected
                toServer.flush();
                launch(args);
            }

//                out.println(scanner.nextLine());

        }

    }


//    private Socket server;
//    private ObjectOutputStream toServer;
//    private ObjectInputStream fromServer;
//    private BufferedReader fromUser;
//
//
//    Client(String serverName) {
//        try {
//            server = new Socket(serverName, 50000);
//            toServer = new ObjectOutputStream(server.getOutputStream());
//            fromServer = new ObjectInputStream(server.getInputStream());
//            fromUser = new BufferedReader(new InputStreamReader(System.in));
//
//            toServer.writeUTF("Connected"); //server connected
//        }
//        catch (UnknownHostException e) {
//            System.out.println("Unknown Host");
//        }
//        catch (IOException e) {
//            System.out.println("I/O error");
//        }
//    }

    //todo no run method
//    public void run(String[] args) {
//        javafx.application.Application.launch(args);
//    }

    //todo Finalise problems
//    @Override
//    public void finalize () {
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



//    public static void main(String[] args) throws IOException {
//
//
//        if (args.length != 1) {
//            System.err.println("Pass the server IP as the sole command line argument");
//            return;
//        }
//
//
////        Client client = new Client(args[0]);
////        client.run(args);
//
//    }


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
                try {
                    toServer.writeUTF("login "+usernameField.getText()+" "+passwordField.getText());
                    toServer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        loginButton.setLayoutX(140);
        loginButton.setLayoutY(200);

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    toServer.writeUTF("register "+usernameField.getText()+" "+passwordField.getText());
                    toServer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }


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