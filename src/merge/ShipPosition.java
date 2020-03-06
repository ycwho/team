package merge;

/**
 * this class is for describe the type and position of a ship
 * @version 2020-02-28
 * @author Yuanchao Hu
 *
 */
public class ShipPosition implements java.io.Serializable{
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
		super();
		this.type = type;
		this.status = status;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	
	


}
