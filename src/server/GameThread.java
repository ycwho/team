package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class GameThread extends Thread {
	private UserThread host;
	private Vector<UserThread> players;
	private Vector<Player> playersInfo;

	Map<String, UserThread> onlineUsers;
	Map<String, GameThread> onlineGames;
	private static int threadCounter = 1;
	public int maxPlayer;
	String gameName;

	boolean turnEnd;

	/**
	 * game status: 1 means waiting to start 2 means game is start 3 means game is
	 * end -1 means game is been closed for some reason
	 */
	private int gameStatus;

	public GameThread(UserThread host, String gameName, int maxPlayer , Map<String, UserThread> onlineUsers,
			Map<String, GameThread> onlineGames) {

		super("Game-" + threadCounter);
		threadCounter++;

		this.host = host;
		this.gameName = gameName;
		this.onlineUsers = onlineUsers;
		this.onlineGames = onlineGames;
		
		this.maxPlayer = maxPlayer;

		players = new Vector();

		playersInfo = new Vector();

		gameStatus = 1;

		joinGame(host);

		turnEnd = true;
	}

	public synchronized void run() {
		gameStatus = 1;
		System.out.println(this.getName() + " is running. host is " + host.getUsername() + "game name :" + gameName);
		int counter = 0;
		int attackerCounter = maxPlayer-1;
		broadcastMessage(Protocol.GAME_NOTICE_CREATE);
		String message = "";


		try {
			while (gameStatus != -1) {
				System.out.println("in loop" + counter++);
				if (gameStatus == 1) {

				}
				// game start
				else if (gameStatus == 2) {
					if (turnEnd) {
						
						playersInfo.get(attackerCounter).setPlayerStatus(3);
						
						attackerCounter = nextAttacker(attackerCounter);

						message = "now it is " + playersInfo.get(attackerCounter).getUsername() + "'s turn.";
						inGameMessage(message);
						playersInfo.get(attackerCounter).setPlayerStatus(4);
						turnEnd = false;

						//nextAttacker(attackerCounter);

					}else {
						message = "now it is " + playersInfo.get(attackerCounter).getUsername() + "'s turn.";
					}
				} else if (gameStatus == 3) {
					System.out.println("game is end");
					inGameMessage("Game is end , winner is " + winner());
					break;
				}
				wait();

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something Wrong with " + this.getName());
		} finally {
			close();
		}

	}

	public synchronized int joinGame(UserThread joiner) {

		if (players.size() < maxPlayer && gameStatus == 1) {
			players.add(joiner);
			playersInfo.add(new Player(joiner.getUsername()));
			checkGameStatus();
			return 0;
		} else {
			return 1;
		}

	}
	
	
	
	public synchronized int leaveGame(UserThread leaver) {
		
		int playerSlot = indexOfPlayer(leaver);
		System.out.println(playerSlot);
		if (playerSlot >= 0) {
			if (gameStatus == 1) {
				
				players.remove(playerSlot);
				playersInfo.remove(playerSlot);
				System.out.println("some one quit game");
				checkGameStatus();
				
				return 0;

			}
			// game is start
			else if (gameStatus == 2) {

				// playersStatus.set(playerSlot, 0);

				gameStatus = -1;
				System.out.println("some one quit game");
				checkGameStatus();
				return 0;
			}
			return 1;
		} else {
			return 1;
		}
		
	}
	
	public synchronized int uploadShips(UserThread uploader, List<Integer> shipPositions) {
		int result = 0;
		
		if (gameStatus == 1) {
			
			int playerSlot = players.indexOf(uploader);
			playersInfo.get(playerSlot).setShips(shipPositions);
		} else {
			result = 1;
		}

		checkGameStatus();
		return result;
	}
	
	public synchronized int attack(UserThread attacker, int defenderSlot, int position) {
		int result = 0;
		int attackerSlot = players.indexOf(attacker);
		
		
		try {

			
			// if it is in attacker's turn
			if (playersInfo.get(attackerSlot).getPlayerStatus() == 4 && turnEnd == false) {
				System.out.println("Player"+attackerSlot + " want to attack " + "Player" + defenderSlot);
				// if this point has not been attacked.
				if (playersInfo.get(defenderSlot).isLegalAttack(position)) {
					playersInfo.get(defenderSlot).addBeAttacked(position);
					
					turnEnd = true;
					inGameMessage("[Player-" + defenderSlot + ", postion-" + position + "] has been attacked,"
							+ " attack ship:" + playersInfo.get(defenderSlot).isHit(position));
					
					//check if the ship sunk,return [player index] [ship index] [positions of the ship]
					int index = playersInfo.get(defenderSlot).checkShipSunk(position);
					if(index >= 0) {
						inGameMessage("[Player-" + defenderSlot + ", Ship - " + index + "] has sunk, positions["
								+ playersInfo.get(defenderSlot).oneShipPositions(index));
					}
					
					if(playersInfo.get(defenderSlot).getPlayerStatus()==5) {
						inGameMessage(playersInfo.get(defenderSlot).getUsername() + " has dead.");
					}
				} else {
					//attack position illegal
					result = 2;
				}
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}

		checkGameStatus();
		return result;
	}

	public synchronized void checkGameStatus() {
		
		try {
			sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (players.size() == 0) {
			gameStatus = -1;
		} else if (gameStatus == 1 && readyPlayer() == maxPlayer) {
			gameStart();

		} else if (deadPlayer() == maxPlayer - 1) {
			gameStatus = 3;
		}

		notify();
	}

	public void gameStart() {
		gameStatus = 2;
		for (Player p : playersInfo) {
			if (p.getPlayerStatus() == 2) {
				p.setPlayerStatus(3);
			}
		}
		inGameMessage("Game Start!");

	}

	public int nextAttacker(int attackerCounter) {

		attackerCounter = attackerCounter < playersInfo.size()-1 ? (attackerCounter+1) : 0;
		
		if(playersInfo.get(attackerCounter).getPlayerStatus() == 3) {
			return attackerCounter;
		}
		
		return nextAttacker(attackerCounter);
	}

	public int indexOfPlayer(UserThread user) {
		System.out.println("enter index-check program, playerList:" + playersInfo.size());
		for (int i = 0; i < playersInfo.size(); i++) {
			System.out.println("check the " + i+ "th elements in players Info");
			if (playersInfo.get(i).getUsername().equals(user.getUsername())) {
				return i;
			}
		}
		return -1;
	}

	public int deadPlayer() {
		int result = 0;
		for (Player p : playersInfo) {
			if (p.getPlayerStatus() == 5) {
				result++;
			}
		}
		return result;

	}
	
	public String winner() {

		for (Player p : playersInfo) {
			if (p.getPlayerStatus() != 5) {
				return p.getUsername();
			}
		}
		return "no man win";
	}

	public int readyPlayer() {
		int result = 0;
		for (Player p : playersInfo) {
			if (p.getPlayerStatus() == 2) {
				result++;
			}
		}
		return result;
	}

	public void inGameMessage(String message) {
		players.forEach((t) -> {
			try {
				t.tellClient("[GAME]" + message);
			} catch (IOException e) {

				e.printStackTrace();
			}
		});
	}
	
	public void broadcastMessage(String message) {
		onlineUsers.forEach((k,v) -> {
			try {
				v.tellClient(message + " " + gameName);
			} catch (IOException e) {

				e.printStackTrace();
			}
		});
	}

	public void close() {
		onlineGames.remove(gameName, this);
		
		players.forEach(e -> e.gameCrash());
		
		broadcastMessage(Protocol.GAME_NOTICE_END);

	}
}
