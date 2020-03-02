package client;

// Usage:
//        java Client hostname
// type a non-zero integer on a line to send it to server
// type a blank line to end client tt

import java.io.*;
import java.net.*;

public class ClientTest {

	// Communication:

	private Socket server;
	// private OutputStreamWriter toServer;
	private BufferedWriter toServer;
	// private InputStreamReader fromServer;
	private BufferedReader fromServer;
	private BufferedReader fromUser;

	private boolean isLogin;

	// The constructor is given the server's name It opens a socket
	// connection to the server and extracts it input and out streams.

	ClientTest(String serverName) {
		try {
			server = new Socket(serverName, 50000);
			// toServer = new DataOutputStream(server.getOutputStream());
			// fromServer = new DataInputStream(server.getInputStream());

			toServer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			error("Unknown host: " + serverName);
		} catch (IOException e) {
			error("Couldn't get I/O for the connection to " + serverName);
		}
		isLogin = false;
		fromUser = new BufferedReader(new InputStreamReader(System.in));
	}

	// finalize method (also called the "destructor")
	// closes all the resources used by the Client before the Client
	// gets destroyed (reclaimed by the garbage collector)

	public void finalize() {
		try {
			// Let server know we are done.
			// Our convention is to send "0" to indicate this.
			
			toServer.write(Protocol.DISCONNECTION);
			toServer.newLine();
			System.out.println("End the connection");

			// Close the streams:

			toServer.close();
			fromServer.close();
			fromUser.close();

			// Close the connection:

			server.close();
		} catch (IOException e) {
			error("Something went wrong ending the client");
		}
	}

	// This is what this class does:

	public void run() {
		try {
			String userInput;
			int serverNumber;

			System.out.println("Connected to Server:" + server.getPort());
			while(true) {
			if (!isLogin) {
				
				
				System.out.println("Please enter userName: ");
				String userName = fromUser.readLine();
				System.out.println("Please enter Password: ");
				String password = fromUser.readLine();
				System.out.println(userName +":"+ password);
				toServer.write(userName);
				//toServer.newLine();
				toServer.write(password);
				toServer.newLine();
				toServer.flush();
				
				System.out.println("Waiting for server respond");
				
				if(fromServer.readLine().equals("log success")) {
					isLogin = true;
					System.out.println("OK , end");
					break;
				}else {
					System.out.println("try again");
				}
				
			}
				
			}

			/*
			 * while ((userInput = fromUser.readLine()) != null) {
			 * 
			 * // We stipulate that a blank line tell the client to end:
			 * 
			 * if (userInput.equals("")) break;
			 * 
			 * int userNumber = Integer.parseInt(userInput);
			 * 
			 * toServer.write(userNumber); tell("sent " + userNumber + " to server");
			 * 
			 * //serverNumber = fromServer.readInt(); //tell("got " + serverNumber +
			 * " from server"); }
			 */
		} catch (IOException e) {
			tell("Socket commmunication broke");
			// finalize();
		}
	}

	private void chooseLogMethod() {
		String userInput;
		try {
			while ((userInput = fromUser.readLine()) != null) {
				
			}
		} catch (Exception e) {
		}
	}

	// helper method to print error messages

	private void error(String message) {
		System.err.println(message);
		System.exit(1); // Don't do this in practice! (Why?)
	}

	// helper method to talk to the user

	private void tell(String message) {
		System.out.println(message);
	}

	// sample main method to create the client

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java LaunchClient hostname");
			System.exit(1);
		}

		ClientTest client = new ClientTest(args[0]);

		client.run();
	}

}
