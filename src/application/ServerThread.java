package application;

import java.net.*;
import java.io.*;
import database.Database;

public class ServerThread extends Thread {

    private Socket clientSocket = null;
    private int playerID;
    private boolean loggedIn;

    public ServerThread(Socket clientSocket, int i) {
        super ("ServerThread");
        this.clientSocket = clientSocket;
        this.playerID = i;
        loggedIn = false;

    }

    public void run() {
        // TODO Auto-generated method stub

        try (
                ObjectOutputStream toClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream fromClient = new ObjectInputStream(clientSocket.getInputStream()))
                //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true))
        {
            if (fromClient.readUTF().equals("Connected")) {
                System.out.println("Connected: " + clientSocket);
            }
            // todo write proper closing of thread from client (eg the exit square from the gui)
            while (true) {
                Database db = new Database();

                    String[] input = fromClient.readUTF().split("\\s");
                    if (input[0] == "login") {
                        System.out.println("test");
                        boolean logInResult = db.checkExistUser(input);
                        if (logInResult) {
                            loggedIn = true;
                            //out.println("loggedIn");
                            System.out.println(loggedIn);
                        } else { // handle specific cases here e.g. -1 or -2
                            //out.println("Please try again");
                        }
                    }
                    if (input[0] == "register") {
                        System.out.println(input[1] + " " + input[2]);
                        System.out.println(db.checkExistUser(input));
//        			int signInResult = db.userSignIn(input[1], input[2]);
//        			if (signInResult >= 0) {
//        				loggedIn=true;
//        				out.println("loggedIn");
                        if (db.checkExistUser(input) == false) {
                            db.insertUser(input);
                            //out.println("registered");
                        }
                    } else { // handle specific cases here e.g. -1 or -2
                        //out.println("Please try again or register");
                    }


//            if(loggedIn= true) {
//            	new GameThread(socket, playerID);
//            }

            }
            //System.out.println("Disconnected: " + clientSocket);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
