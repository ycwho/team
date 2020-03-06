package application;



import java.io.*;

import java.net.*;
import java.util.Arrays;
import javafx.application.Platform;

public class Client {

	// Communication:

	private Socket server;
	// private OutputStreamWriter toServer;
	public BufferedWriter toServer;
	// private InputStreamReader fromServer;
	private BufferedReader fromServer;
	private BufferedReader fromUser;
	ListenFromSystem printer;
	private boolean isLogin;
	private Controller controller;
	private MainMenuController mainMenuController;
	private Main main;
	
	Client(String serverName, Main main) {
		try {
			this.main = main;
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
		Thread fromServerThread = new Thread(new FromServer(fromServer, main));
		fromServerThread.start();
		
		
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

	public void setController(Controller controller) {
		// TODO Auto-generated method stub
		this.controller = controller;
	}

	public void setMainMenuController(MainMenuController controller2) {
		// TODO Auto-generated method stub
		this.mainMenuController = controller2;
	}

	public void register(String registerToServer) throws IOException {
		// TODO Auto-generated method stub
		toServer.write("log Jim Pass");
	}


	public void login(String loginToServer) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("hi");
		write(loginToServer);
		
	}
	
	public void write(String message) throws IOException {
		toServer.write(message);
		toServer.newLine();
		toServer.flush();
	}
	
	class FromServer implements Runnable {
		
		BufferedReader fromServer;
		String[] nextLine;
		Main main;
		
		public FromServer(BufferedReader fromServer, Main main) {
			this.fromServer = fromServer;
			this.main=main;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				
					try {
						
						String[] nextLine = fromServer.readLine().split(" ");
						System.out.println(nextLine);
						if(nextLine[0].equals("login")) {
							if(nextLine[1].equals("success")) {
								System.out.println("you are logged in");
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										try {
											main.setMainMenuStage();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});
								
							}
						}
						if(nextLine[0].equals("register")) {
							if(nextLine[1].equals("1")) {
								System.out.println("you can now log in");
								
								
							}
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("From Server: " + Arrays.toString(nextLine));
					
			}
		}
	}	
//	public static void main(String[] args) {
//		if (args.length != 1) {
//			System.err.println("Usage: java LaunchClient hostname");
//			System.exit(1);
//		}
//
//		ClientTest2 client = new ClientTest2(args[0]);
//
//		client.run();
//	}

}
