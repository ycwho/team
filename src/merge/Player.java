package merge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	
	private Set<Integer> shipPositions;

	private String username;
	
	private int hited;

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
	
	
	

	
	public void setShips(Set<Integer> shipPositions) {
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
	
	
}
