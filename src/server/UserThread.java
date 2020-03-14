package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;



public class UserThread extends Thread {

	BufferedReader fromClient;
	BufferedWriter toClient;
	private Socket client;
	private DatabaseTest database;

	// 0 means not log in, 1 means log in
	private int userStatus;
	private String username;
	public static int threadCounter = 1;
	Map<String, UserThread> onlineUsers;
	Map<String, GameThread> onlineGames;
	GameThread joinedGame;

	public UserThread(Socket client, Map<String, UserThread> onlineUsers, Map<String, GameThread> onlineGames) {
		super("UserThread-" + threadCounter);
		threadCounter++;
		this.client = client;

		this.onlineUsers = onlineUsers;
		this.onlineGames = onlineGames;
		userStatus = 0;

		System.out.println("Create " + this.getName());
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	public void run() {
		try {

			database = new DatabaseTest();
			database.run();
			toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

			String getCommand;

			System.out.println("waiting for client respond");

			while (!(getCommand = fromClient.readLine()).equals(Protocol.DISCONNECTION)) {
				if (userStatus == 0) {
					tellClient("[REPLY]"+logCommand(getCommand));
				} else if (userStatus == 1) {
					tellClient("[REPLY]"+userCommand(getCommand));
				} else if (userStatus == 2) {
					tellClient("[REPLY]"+gameCommand(getCommand));
				}

			}

			System.out.println(Thread.currentThread().getName() + ":" + username + "Client want to disconnect");

		}

		catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + ":" + username
					+ " Something went wrong. Ending service to client...");

		} finally {
			close();
		}
	}

	public String logCommand(String getCommand) {
		int i = 0;
		String[] commandElements = getCommand.split(" ");
		// login
		if (getCommand.startsWith(Protocol.CLIENT_LOGIN)) {

			String username = commandElements[1];
			String password = commandElements[2];
			if (commandElements.length != 3) {
				return Protocol.CLIENT_NEED_RESENT_COMMAND;
			}
			if (onlineUsers.containsKey(username)) {
				return "have been logged";
			}

			i = database.userLogIn(username, password);
			if (i == 0) {
				userStatus = 1;
				this.username = username;
				onlineUsers.forEach((k, v) -> {
					try {
						v.tellClient(Protocol.SERVER_NOTICE_OTHER_LOGIN + username);
					} catch (IOException e) {

						e.printStackTrace();
					}
				});

				onlineUsers.put(username, this);
				
			}
			return Protocol.CLIENT_LOGIN_REPLY[i];

		}
		// sign up
		else if (getCommand.startsWith(Protocol.CLIENT_SIGNUP)) {

			String username = commandElements[1];
			String password = commandElements[2];

			i = database.userSignIn(username, password);

			return Protocol.CLIENT_SIGNUP_REPLY[i];
		}
		return Protocol.CLIENT_NEED_RESENT_COMMAND;
	}

	public String userCommand(String getCommand) {
		int i = 0;
		String[] commandElements = getCommand.split(" ");

		// check online user
		if (getCommand.startsWith(Protocol.CLIENT_CHECK_ONLINE_USER)) {

			String result = "other online users : ";

			for (String a : onlineUsers.keySet()) {
				result += a + " ";
			}
			result += "online games : ";
			for (String a : onlineGames.keySet()) {
				result += a + " ";
			}

			return result;

		}

		// create game
		else if (getCommand.startsWith(Protocol.CLIENT_CREATE_GAME)) {
			String gameName = commandElements[1];
			int maxPlayer = Integer.parseInt(commandElements[2]);
			if (!onlineGames.containsKey(gameName)) {

				try {
					GameThread newGame = new GameThread(this, gameName, maxPlayer, onlineUsers, onlineGames);
					newGame.start();
					
					onlineGames.put(gameName, newGame);
					joinedGame = newGame;
					userStatus = 2;

				} catch (Exception e) {
					e.printStackTrace();
					i = 2;
				}
			} else {
				i = 1;
			}

			return Protocol.CLIENT_CREATE_REPLY[i];
		}

		// join game
		else if (getCommand.startsWith(Protocol.CLIENT_JOIN_GAME)) {
			String gameName = commandElements[1];
			if (!onlineGames.containsKey(gameName)) {
				i = 1;
			} else {

				try {
					onlineGames.get(gameName).joinGame(this);
					joinedGame = onlineGames.get(gameName);
					userStatus = 2;
				} catch (Exception e) {
					e.printStackTrace();
					i = 2;
				}
			}

			return Protocol.CLIENT_JOIN_REPLY[i];
		}

		return Protocol.CLIENT_NEED_RESENT_COMMAND;
	}

	public String gameCommand(String getCommand) {
		int result = 0;
		String[] commandElements = getCommand.split(" ");

		// leave game
		if (getCommand.startsWith(Protocol.CLIENT_QUIT_GAME)) {
			userStatus = 1;
			result = joinedGame.leaveGame(this);
			

			return Protocol.CLIENT_QUIT_REPLY[result];
		}
		
		//upload
		else if(getCommand.startsWith(Protocol.CLIENT_UPLOAD_SHIP_POSITIONS)) {
			List<Integer> data = new ArrayList();
			try {
				for(int i = 1; i < commandElements.length; i++) {
					data.add(Integer.parseInt(commandElements[i]));
				}
			}catch(Exception e) {
				return Protocol.CLIENT_NEED_RESENT_COMMAND;
			}
			
			result = joinedGame.uploadShips(this, data);
			
			return Protocol.CLIENT_UPLOAD_REPLY[result];
		}
		
		//attack
		else if(getCommand.startsWith(Protocol.CLIENT_ATTACK)) {

			try {
				int defenderSlot = Integer.parseInt(commandElements[1]);
				int position = Integer.parseInt(commandElements[2]);
				
				result = joinedGame.attack(this, defenderSlot, position);
				
			}catch(Exception e) {
				return Protocol.CLIENT_NEED_RESENT_COMMAND;
			}
			

			return Protocol.CLIENT_ATTACK_REPLY[result];
		}

		return Protocol.CLIENT_NEED_RESENT_COMMAND;
	}
	
	public void gameCrash() {
		if(userStatus ==2)
			userStatus =1;
		
		
		try {
			tellClient("game is close");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public synchronized void tellClient(String message) throws IOException {
		toClient.write(message);
		toClient.newLine();
		toClient.flush();

	}

	public void close() {

		if (userStatus >= 1) {
			onlineUsers.remove(username, this);

			onlineUsers.forEach((k, v) -> {
				try {
					v.tellClient(Protocol.SERVER_NOTICE_OTHER_LOGOUT + username);
				} catch (IOException e) {

					e.printStackTrace();
				}
			});

		}
		if (userStatus >= 2) {
			try {
				joinedGame.leaveGame(this);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			toClient.close();
			fromClient.close();
			client.close();
			database.quit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
