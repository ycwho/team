package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class Player {
	
	/**
	 * player status:
	 * 
	 * 1 means player has joined the game and waiting to upload the ship position
	 * 2 means player has uploaded the data and waiting the game start
	 * 3 means player is waiting other players to make their attack
	 * 4 means the player is ready to attack
	 * 5 means the player is dead
	 */
	private int playerStatus;
	
	private Set<Integer> beAttacked;
	
	private List<Integer> shipPositions;
	
	
	private int[] ShipBeAttackedCounter= new int[Protocol.SHIPS_LENGTH.length];
	//every ships first index [0, 5, 9, 12, 15]
	private int[] shipsIndex = new int[Protocol.SHIPS_LENGTH.length];

	private String username;
	
	private int hited;

	public Player(String username) {
		
		playerStatus = 0;
		beAttacked = new TreeSet();
		hited = 0;
		this.username = username;

		
		
		for(int a : ShipBeAttackedCounter) {
			a = 0;
		}
		
		shipsIndex[0] = 0;
		for(int i = 1; i < Protocol.SHIPS_LENGTH.length; i++) {
			shipsIndex[i] =  shipsIndex[i-1]+Protocol.SHIPS_LENGTH[i-1];
		}
	}
	
	
	
	/**
	 * @return the playerStatus
	 */
	public int getPlayerStatus() {
		return playerStatus;
	}

	

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}



	/**
	 * @param playerStatus the playerStatus to set
	 */
	public void setPlayerStatus(int playerStatus) {
		this.playerStatus = playerStatus;
	}
	
	


	public void setUsername(String username) {
		this.username = username;
		setPlayerStatus(1);
	}
	
	
	

	
	public void setShips(List<Integer> shipPositions) {
		this.shipPositions = shipPositions;

		
		
		
		
		setPlayerStatus(2);
	}
	
	public boolean isLegalAttack(int position) {
		
		
		return position >= 1 && position <= 100 && !getBeAttacked().contains(position) && playerStatus == 3;
	}
	
	public boolean isHit(int hit) {
		for(int position:shipPositions) {
			if(position == hit) {
				return true;
			}
			
		}
		return false;
		
	}
	
	/**
	 * @param beAttacked the beAttacked to set
	 */
	public void addBeAttacked(int position) {
		beAttacked.add(position);
		if(isHit(position)) {
			hited++;
			if(hited == shipPositions.size()) {
				setPlayerStatus(5);
			}
		}
	}



	/**
	 * @return the beAttacked
	 */
	public Set<Integer> getBeAttacked() {
		return beAttacked;
	}
	
	
	/*
	 * their are 5 ships : 0 , 1 ,2 ,3, 4
	 * their lengths :     5 , 4 ,3 ,3 ,2
	 * 
	 */
	public int checkShipSunk(int position) {
		int index = findBeAttackedShip(position);
		ShipBeAttackedCounter[index] += 1;
		
		
		return ShipBeAttackedCounter[index] >= Protocol.SHIPS_LENGTH[index] ? index : -1;
		
	}
	
	public int findBeAttackedShip(int attackPoint) {
		int a = shipPositions.indexOf(attackPoint);
		for(int i = 0; i < Protocol.SHIPS_LENGTH.length; i++) {
			if(a < Protocol.SHIPS_LENGTH[i]) {
				return i;
			}
			else {
				a = a - Protocol.SHIPS_LENGTH[i];
			}
			
		}
		System.out.println("Why i cannot find the be attacked ship?");
		return 0;
	}
	
	public String oneShipPositions(int index) {
		String result = new String();
		for(int i = shipsIndex[index]; i < shipsIndex[index] + Protocol.SHIPS_LENGTH[index]; i++) {
			result += shipPositions.get(i) + " ";
		}
		return result;
	}
	
	
}
