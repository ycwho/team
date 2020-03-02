package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import database.Database;
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
        		Database db = new Database();
        		String[] input = in.nextLine().split("\\s");
        		System.out.println(db.checkExistUser(input));
        		if(input[0] == "login") {
        			boolean logInResult = db.checkExistUser(input);
        			if (!logInResult) {
        				loggedIn=true;
        				out.println("loggedIn");
        			}
        			else { // handle specific cases here e.g. -1 or -2
        				out.println("Please try again");
        			}
        		}
        		if(input[0] == "register") {
//        			int signInResult = db.userSignIn(input[1], input[2]);
//        			if (signInResult >= 0) {
//        				loggedIn=true;
//        				out.println("loggedIn");
        			if(!db.checkExistUser(input)) {
        				db.insertUser(input);
        				out.println("registered");
        			}
        			}
            	else { // handle specific cases here e.g. -1 or -2
            		out.println("Please try again or register");
            	}
            }
//            if(loggedIn= true) {
//            	new GameThread(socket, playerID);
//            }
        } 
        	
         catch (Exception e) {
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
