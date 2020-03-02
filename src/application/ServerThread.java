package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import database.DatabaseTest;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerThread implements Runnable {
	
	private Socket socket;
	private int playerID;
	private boolean loggedIn;
	
	public ServerThread(Socket socket, int i) {
		
		this.socket = socket;
		this.playerID = i;
		loggedIn = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Connected: " + socket);
        try {
        	while(true) {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String[] input = in.nextLine().split("\\s");
            if(input[0] == "login") {
            	Database db = new Database();
            	db.userSignIn(input[1], input[2]);
            	loggedIn = true;
            }
            if(loggedin= true) {
            	new GameThread(socket, playerID);
            }
        	} // connect to database check
        	
        } catch (Exception e) {
            System.out.println("Error:" + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }
	}
}
