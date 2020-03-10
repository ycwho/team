package application;


import server.ListenFromSystem;
import server.Protocol;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {

	// Communication:

	private Socket server;
	// private OutputStreamWriter toServer;
	private BufferedWriter toServer;
	// private InputStreamReader fromServer;
	private BufferedReader fromServer;
	private BufferedReader fromUser;
	ListenFromSystem printer;
	private boolean isLogin;

	ClientTest(String serverName) {
		try {
			server = new Socket(serverName, 50000);
			// toServer = new DataOutputStream(server.getOutputStream());
			// fromServer = new DataInputStream(server.getInputStream());

			toServer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

			printer = new ListenFromSystem(fromServer);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + serverName);
		} catch (IOException e) {
			System.out.println("Couldn't get I/O for the connection to " + serverName);
		}


		isLogin = false;
		fromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Connected to Server:" + server.getPort());



	}

	public void finalize() {
		try {
			// Let server know we are done.
			// Our convention is to send "0" to indicate this.

			toServer.write(Protocol.DISCONNECTION);
			toServer.newLine();
			toServer.flush();
			System.out.println("End the connection");

			// Close the streams:

			toServer.close();
			fromServer.close();
			fromUser.close();

			// Close the connection:

			server.close();
		} catch (IOException e) {
			System.out.println("Something went wrong ending the client");
		}
	}

	// This is what this class does:

	public void run() {
		try {
			ListenFromSystem printer = new ListenFromSystem(fromServer);
			printer.start();
			System.out.println("Please enter command: ");
			while (true) {

				
				String command = fromUser.readLine();
				if (!command.startsWith("END")) {

					toServer.write(command);
					toServer.newLine();
					toServer.flush();

					// System.out.println("Waiting for server respond...");




				} else {
					finalize();
				}

			}

		} catch (IOException e) {
			
			 finalize();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java LaunchClient hostname");
			System.exit(1);
		}

		ClientTest client = new ClientTest(args[0]);

		client.run();
	}

}
