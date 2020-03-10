package database;


public class ShipPosition {
	private int type;
	private int status;
	private int xPosition;
	private int yPosition;
	
	/**
	 * @param type 
	 * @param status 0 means not used in the battle field, 1 means the ship is horizontal (parallel with x-axis), 2 means vertical
	 * @param xPosition the coordinates of the ship (head) in x-axis
	 * @param yPosition	the coordinates of the ship (head) in y-axis
	 */
	public ShipPosition(int type, int status, int xPosition, int yPosition) {

		this.type = type;
		this.status = status;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	
	


}
