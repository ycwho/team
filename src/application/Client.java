package application;



import java.io.*;

import java.net.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
		write(registerToServer);
	}


	public void login(String loginToServer) throws IOException {
		// TODO Auto-generated method stub
		write(loginToServer);
	}

	public void checkOnline(String checkServer) throws IOException {
		// TODO Auto-generated method stub
		write(checkServer);
	}
	
	public void checkGames(String checkServer) throws IOException {
		// TODO Auto-generated method stub
		write(checkServer);
	}

	public void createGame(String createGameToServer) throws IOException {
		// TODO Auto-generated method stub
		write(createGameToServer);
	}

	public void joinGame(String joinRequest) throws IOException {
		// TODO Auto-generated method stub
		write(joinRequest);
	}

	public void saveShipPositions(String shipPositions) throws IOException {
		// TODO Auto-generated method stub
		write(shipPositions);
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
						String command = fromServer.readLine();
						String[] nextLine = command.split(" ");
						System.out.println(nextLine[0]);
//						System.out.println(nextLine[1]);
//						System.out.println(nextLine);
						if (command.startsWith(Protocol.CLIENT_NEED_RESENT_COMMAND)){
							System.out.println(command);
						}
						else {
							if (nextLine[0].equals("[REPLY]login")) {
								if (nextLine[1].equals("success")) {
									System.out.println("you are logged in");
									Platform.runLater(new Runnable() {
										@Override
										public void run() {
											try {
												main.setMainMenuStage();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												System.out.println("error switchign stages");
											}
										}
									});

								}
							}
							if (command.startsWith("[REPLY]register")) {
								if (nextLine[1].equals("success")) {
									System.out.println("you can now log in");
								}
							}
							if (command.startsWith("[REPLY]other")) {
								String toTextArea = "Online users:\n";
								System.out.println("Online users:");
								for (int i = 3; i < nextLine.length; i++) {
									System.out.println(nextLine[i]);
									toTextArea += nextLine[i] + "\n";
								}
								mainMenuController.setTextArea(toTextArea);
							}
							if (command.startsWith("[REPLY]Games:")) {
								String toTextArea = "Games:\n";
								System.out.println("Games:");
								if (nextLine.length > 1) {
									for (int i = 1; i < nextLine.length; i++) {
										System.out.println(nextLine[i]);
										toTextArea += nextLine[i] + "\n";
									}
								}
								mainMenuController.setGamesListTextArea(toTextArea);
							} else if (command.startsWith(Protocol.CLIENT_CREATE_REPLY[0])) {
								System.out.println("Game created, waiting for other player(s)");
							} else if (command.startsWith(Protocol.CLIENT_CREATE_REPLY[1])) {
								System.out.println("Game Already Exists");
							} else if (command.startsWith(Protocol.CLIENT_CREATE_REPLY[2])) {
								System.out.println("Game Creation Failed");
							} else if (command.startsWith(Protocol.CLIENT_JOIN_REPLY[0])) {
								System.out.println("Game Joined");
							} else if (command.startsWith(Protocol.CLIENT_JOIN_REPLY[1])) {
								System.out.println("No Game Found With That Name");
							} else if (command.startsWith(Protocol.CLIENT_JOIN_REPLY[2])) {
								System.out.println("Game Join Unsuccessful");
							}

							if (command.startsWith(Protocol.SERVER_NOTICE_OTHER_LOGIN)){
								//game.broadcast(nextLine[1] + " has logged in")
							}

							if (command.startsWith(Protocol.SERVER_NOTICE_OTHER_LOGOUT)){
								//game.broadcast(nextLine[1] + " has logged out")
							}

							if (command.startsWith(Protocol.TURN)){
								//game.broadcast(nextLine[1] + "'s turn")
							}

							if (command.startsWith(Protocol.HIT)){
								int position = Integer.parseInt(nextLine[2]);
								boolean hit = nextLine[3].equals("true");
								//game.hit(nextLine[1],position,hit);
							}

							if (command.startsWith(Protocol.SHIP_SUNK)){
								//game.broadcast(nextLine[1] + " sunk")
							}

							if (command.startsWith(Protocol.PLAYER_DEAD)){
								//game.broadcast(nextLine[1] + " sunk")
							}

							if (command.startsWith(Protocol.GAME_OVER)){
								System.out.println(nextLine[1]);
								//game.broadcast(command);
								//pauses for 3 seconds then takes back to main menu
								try
								{ Thread.sleep(3000);
								} catch(InterruptedException ex)
								{ Thread.currentThread().interrupt();
								}
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										try {
											main.setMainMenuStage();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											System.out.println("error switching stages");
										}
									}
								});
							}

							if (command.startsWith(Protocol.GAME_NOTICE_CREATE)){
								System.out.println(command);
							}

							if (command.startsWith(Protocol.GAME_NOTICE_END)){
								System.out.println(command);
							}

							if (command.startsWith(Protocol.CLIENT_QUIT_REPLY[0])){
								System.out.println(command);
							}

							if (command.startsWith(Protocol.CLIENT_QUIT_REPLY[1])){
								System.out.println(command);
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
