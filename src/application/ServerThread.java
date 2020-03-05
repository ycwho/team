package application;

import java.net.*;
import java.io.*;
import database.Database;

public class ServerThread extends Thread {

    private Socket clientSocket;
    private int playerID;
    private boolean loggedIn;
    private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private String[] input;
	private Database db;

    public ServerThread(Socket clientSocket, int i) {
        super ("ServerThread");
        this.clientSocket = clientSocket;
        this.playerID = i;
        loggedIn = false;

    }

    public void run() {
        // TODO Auto-generated method stub

        try
                //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true))
        {
        	toClient = new ObjectOutputStream(clientSocket.getOutputStream());
            fromClient = new ObjectInputStream(clientSocket.getInputStream());
            db = new Database();
        }catch (IOException e) {
            	e.printStackTrace();
            }
            // todo write proper closing of thread from client (eg the exit square from the gui)
           try {
        	   
        	   while(true) {
        		   
        		  
        		   try {
        		   input = (String[]) fromClient.readObject();
        		   } catch (EOFException eof) {
        			  String[] empty = {""};
        			  input = empty;
        		   }
        		   if(input[0] != "" ) {
        			   System.out.println(input[0]);
        		   }
        		   if (input[0].equals("Connected")){
                       System.out.println("Connected: " + clientSocket);
                   }
                    if (input[0].equals("login")) {
                        System.out.println(input[0]+" "+input[1]+" "+input[2]);
                        boolean logInResult = db.checkExistUser(input[1], input[2]);
                        boolean passwordCorrect = db.checkPassword(input[1], input[2]);
                        System.out.println("password check: " + passwordCorrect);
                        if (logInResult && passwordCorrect) {
                            loggedIn = true;
                            //out.println("loggedIn");
                            String[] returnString = {"login", "1"};
                            toClient.writeObject(returnString);
                            System.out.println(loggedIn);
                        } else { // handle specific cases here e.g. -1 or -2
                            //out.println("Please try again");
                        }
                    }
                    if (input[0].equals("register")) {
                        System.out.println(input[1] + " " + input[2]);
                        boolean userAlreadyExists = db.checkExistUser(input[1], input[2]);
                        System.out.println("user exists already: " + userAlreadyExists);
                        if (userAlreadyExists == false) {
                            db.insertUser(input[1], input[2]);
                            //out.println("registered");
                        }
                    } else { // handle specific cases here e.g. -1 or -2
                        //out.println("Please try again or register");
                    }


//            if(loggedIn= true) {
//            	new GameThread(socket, playerID);
//            }

        	   }   
        } catch (IOException | ClassNotFoundException e) {
        	e.printStackTrace();
        }


    }

}
