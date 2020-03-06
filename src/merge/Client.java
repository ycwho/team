package merge;



import java.io.*;
import java.net.*;

public class Client {

	// Communication:

	private Socket server;
	// private OutputStreamWriter toServer;
	private BufferedWriter toServer;
	// private InputStreamReader fromServer;
	private BufferedReader fromServer;
	private BufferedReader fromUser;
	ListenFromSystem printer;
	private boolean isLogin;
	private Controller controller;
	private MainMenuController mainMenuController;

	Client(String serverName, Main main) {
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

	public void setController(Controller controller) {
		// TODO Auto-generated method stub
		this.controller = controller;
	}

	public void setMainMenuController(MainMenuController controller2) {
		// TODO Auto-generated method stub
		this.mainMenuController = controller2;
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
