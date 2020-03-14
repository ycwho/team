package application;

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
	
	
	private int[] shipBeAttackedCounter;

	
	List<Integer> shipLength;
	List<Set<Integer>> eachShipPosition;
	
	private String username;
	
	private int hited;
	
	private String positionString;

	public Player(String username) {
		
		playerStatus = 0;
		beAttacked = new TreeSet();
		hited = 0;
		this.username = username;

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
	
	
	//format: s1p1+s1p2-s2p1+s2p2-s3p1+s3p2
	public void setShips(String positionString) throws NumberFormatException{
		int shipNumber = positionString.split("-").length; 
		
		//initialize counter
		shipBeAttackedCounter= new int[shipNumber];
		
		shipLength = new ArrayList();
		shipPositions = new ArrayList();
		
		//split by "-"
		for(String singleShipPositions : positionString.split("-")) {
			shipLength.add(singleShipPositions.split(".").length);
			TreeSet<Integer> a = new TreeSet();
			
			//split by "+"
			for(String singlePosition : singleShipPositions.split(".")) {
				shipPositions.add(Integer.parseInt(singlePosition));
				a.add(Integer.parseInt(singlePosition));
			}
			eachShipPosition.add(a);
		}
		
		
		
		setShips(shipPositions);
		
		
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
		shipBeAttackedCounter[index] += 1;
		
		
		return shipBeAttackedCounter[index] >= shipLength.get(index) ? index : -1;
		
	}
	
	public int findBeAttackedShip(int attackPoint) {

		for(int i = 0; i < eachShipPosition.size(); i++) {
			if(eachShipPosition.get(i).contains(attackPoint)) {
				return i;
			}
		}
		System.out.println("Why i cannot find the be attacked ship?");
		return 0;
	}
	
	public String oneShipPositions(int index) {
		String result = new String();
		for(int position : eachShipPosition.get(index)) {
			result += position + " ";
		}
		return result;
	}
	
	
}
