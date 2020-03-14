package application;

import java.io.*;

import java.lang.reflect.Array;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import database.Database;
import application.Protocol;

//import database.Database;
//import database.DatabaseTest;

public class UserThread extends Thread {

	BufferedReader fromClient;
	BufferedWriter toClient;
	private Socket client;
	private Database database;

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
//			database = new DatabaseTest();
//			database.run();
			database = new Database();
			System.out.println("databaseup");
			toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

			String getCommand = null;

			System.out.println("waiting for client respond");

//			while (!(getCommand = fromClient.readLine()).equals(Protocol.DISCONNECTION)) {
//				if (userStatus == 0) {
//					System.out.println("hi");
//					tellClient("[REPLY]"+logCommand(getCommand));
//				} else if (userStatus == 1) {
//					tellClient("[REPLY]"+userCommand(getCommand));
//				} else if (userStatus == 2) {
//					tellClient("[REPLY]"+gameCommand(getCommand));
//				}
//				
//
//			}
			while (true) {
				
				System.out.println("listening to client");
				getCommand = fromClient.readLine();
				System.out.println(getCommand);
				if(getCommand != null)
					System.out.println(getCommand);
				if (userStatus == 0) {
					//tellClient("[REPLY]"+logCommand(getCommand));
					tellClient(logCommand(getCommand));
				} else if (userStatus == 1) {
					//tellClient("[REPLY]"+userCommand(getCommand));
					tellClient(userCommand(getCommand));
				} else if (userStatus == 2) {
					//tellClient("[REPLY]"+gameCommand(getCommand));
					tellClient(gameCommand(getCommand));
				}
				

			}

//			System.out.println(Thread.currentThread().getName() + ":" + username + "Client want to disconnect");

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
		System.out.println(getCommand);
		String[] commandElements = getCommand.split(" ");
		// login
		if (getCommand.startsWith(Protocol.CLIENT_LOGIN)) {
			System.out.println("revieved login request from client");
			String username = commandElements[1];
			String password = commandElements[2];
//			if (commandElements.length != 3) {
//				return Protocol.CLIENT_MISSING_LOGIN_INFORMATION;
//			}
			if (onlineUsers.containsKey(username)) {
				return "have been logged";
			}
			boolean logInResult = database.checkExistUser(commandElements[1], commandElements[2]);
            boolean passwordCorrect = database.checkPassword(commandElements[1], commandElements[2]);
            System.out.println("password check: " + passwordCorrect);
            if (logInResult && passwordCorrect) {
                this.userStatus = 1;
    			System.out.println("logged in");
                this.username = commandElements[1];
                onlineUsers.forEach((k, v) -> {
					try {
						v.tellClient(Protocol.SERVER_NOTICE_OTHER_LOGIN + username);
					} catch (IOException e) {

						e.printStackTrace();
					}
				});
				onlineUsers.put(username, this);
				return Protocol.CLIENT_LOGIN_REPLY[0] + " " + this.username;
//                String[] returnString = {"login", "1"};
//                toClient.writeObject(returnString);
//                System.out.println(loggedIn);
            } else if (!logInResult) {
				return Protocol.USER_NAME_MISSING;
			} else {
            	return Protocol.PASSWORD_INCORRECT;
            	// handle specific cases here e.g. -1 or -2
                //out.println("Please try again");
            } /////incorrect password etc
           
		}
		//sign up
		else if (commandElements[0].equals(Protocol.CLIENT_SIGNUP)) {
			System.out.println("register request recieved");
			String username = commandElements[1];
			String password = commandElements[2];
			boolean userAlreadyExists = database.checkExistUser(commandElements[1], commandElements[2]);
            System.out.println("user exists already: " + userAlreadyExists);
            if (userAlreadyExists == false) {
                database.insertUser(commandElements[1], commandElements[2]);
                System.out.println("registered");
                return Protocol.CLIENT_SIGNUP_REPLY[0];
            }
            else {
                return Protocol.CLIENT_SIGNUP_REPLY[1];
            }
		}
//		// sign up
//		else if (getCommand.startsWith(Protocol.CLIENT_SIGNUP)) {
//
//			String username = commandElements[1];
//			String password = commandElements[2];
//
//			i = database.userSignIn(username, password);
//
//			return Protocol.CLIENT_SIGNUP_REPLY[i];
//		}
//		return Protocol.CLIENT_NEED_RESENT_COMMAND;
		 return Protocol.CLIENT_NEED_RESENT_COMMAND;
	}

	public String userCommand(String getCommand) {
		int i = 0;
		String[] commandElements = getCommand.split(" ");

		// check online user
		if (getCommand.equals(Protocol.CLIENT_CHECK_ONLINE_USER)) {
			System.out.println("userCommand block reached");
			String result = Protocol.CLIENT_CHECK_ONLINE_USER_RESPONSE;

			for (String a : onlineUsers.keySet()) {
				result += a + " ";
			}
			//check online games
//			result += "online games : ";
//			for (String a : onlineGames.keySet()) {
//				result += a + " ";
//			}
//			String result = "other online users: you";
			return result;

		}
		if (getCommand.equals(Protocol.CLIENT_LOGOUT)) {
			onlineUsers.remove(username);
			this.username = null;
			this.userStatus = 0;
			
		}
		if (getCommand.equals(Protocol.CLIENT_CHECK_GAME)) {
			System.out.println("gameCommand block reached");
			String result = Protocol.CLIENT_CHECK_GAME_RESPONSE;
//			for (String a : onlineUsers.keySet()) {
//				result += a + " ";
//			}
			//check online games
//			result += "online games : ";
			for (String a : onlineGames.keySet()) {
				result += a + " ";
			}
			System.out.println(result);
//			String result = "other online users: you";
			return result;

		}

		// create game
		else if (getCommand.startsWith(Protocol.CLIENT_CREATE_GAME)) {
			String gameName = commandElements[2];
			if (!onlineGames.containsKey(gameName)) {
				try {
                    int playerNum = Integer.parseInt(commandElements[1]);
                    System.out.println("try1");
					GameThread newGame = new GameThread(this, gameName, playerNum, onlineUsers, onlineGames);
					newGame.start();
					onlineGames.put(gameName, newGame);
					joinedGame = newGame;
					userStatus = 2;
					//change this to when game starts
                    onlineUsers.forEach((k, v) -> {
                        try {
                            v.tellClient(Protocol.SERVER_NOTICE_NEW_GAME + gameName);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    });
                    System.out.println("finished creating game");

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

		else if (getCommand.startsWith(Protocol.PLAYER_NAME_REQUEST)) {
			String nameString = Protocol.PLAYER_NAMES;
			nameString += username + "/";
			Vector players = joinedGame.getPlayers();
			Iterator<UserThread> iterator = players.iterator();
			while(iterator.hasNext()){
				nameString += iterator.next().getUsername() + " ";
			}
			return nameString;
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
//			List<Integer> data = new ArrayList();
//			try {
//				for(int i = 1; i < commandElements.length; i++) {
//					data.add(Integer.parseInt(commandElements[i]));
//				}
//			}catch(Exception e) {
//				return Protocol.CLIENT_NEED_RESENT_COMMAND;
//			}
//
//			result = joinedGame.uploadShips(this, data);
//
//
//			//database.saveShipPosition(ShipPosition[] positions, int slot)
//
//
//
//			return Protocol.CLIENT_UPLOAD_REPLY[result];

            try {
                result = joinedGame.uploadShips(this, commandElements[1]);
            }catch(NumberFormatException e) {
                return Protocol.CLIENT_NEED_RESENT_COMMAND;
            }

            return Protocol.CLIENT_UPLOAD_REPLY[result];
        }

        //load(form database to game)
        else if (getCommand.startsWith("load")) {
            String loadPositions = "EMPTY"; //database.loadShipPosition(getCommand);
            if(loadPositions.equals("EMPTY")) {
                return "empty slot!";
            }


            result = joinedGame.uploadShips(this, loadPositions);

            return Protocol.LOAD_POSITIONS_RESPONSE + " " + loadPositions;
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

//	public int playerNumber() throws  IOException{
//		tellClient("Select Number of Players (2-4)");
//		String input = fromClient.readLine();
//		try {
//				int number = Integer.parseInt(input);
//				if (2 <= number && number <=4) {
//					tellClient(number + " players Selected");
//					return number;
//				}
//				else {
//					tellClient("Select a number between 1 and 4");
//					return playerNumber();
//				}
//			}catch (NumberFormatException e) {
//				tellClient(input + "Not a number");
//				return playerNumber();
//			}
//
//
//
//    }

    public String getUserName() {
		return this.username;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
