package application;

import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;
import database.DatabaseTest;


public class Server {

	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		//ArrayList<String> a = new ArrayList();
		//ArrayList<UserThread> onlineUsers = new ArrayList();
		//ArrayList<GameThread> onlineGames = new ArrayList();
		
		Map<String, UserThread> onlineUsers = new ConcurrentHashMap();
		Map<String, GameThread> onlineGames = new ConcurrentHashMap();

		// This will be shared by all clients:

		// Open a server socket:
		try {
			serverSocket = new ServerSocket(50000);
			System.out.println("server online");
		} catch (IOException e) {
			
			System.err.println("Couldn't listen on port: 50000.");
			System.exit(-1);
			
		}

		// Listen to the socket, accepting connections from new clients,
		// and running a new thread to serve each new client:
		try {
			int i = 0;
			while (true) {
				i++;

				Socket clientSocket = serverSocket.accept();


				// get stuck until somebody connects
				UserThread s = new UserThread(clientSocket, onlineUsers, onlineGames);
				s.start();
				//new Thread(new UserThread(clientSocket, i)).start();
			}
		} catch (Exception e) {

		}

	}

}
