/**
 * 
 */
package database;

/**
 * @version 2020-02-28
 * @author Yuanchao Hu
 *
 */
public interface DatabaseInterface {
	
	/**
	 * run a database class
	 * @return 0: connect successfully 
	 *         1: wrong url,time out exception
	 *         2: other problem
	 */
	public int run();
	
	/**
	 * stop a database class
	 * @return 0:disconnect successfully
	 *         1:failed because disconnected already
	 *         2:other problem
	 */
	public int quit();
	
	/**
	 * sign in for a new user
	 * Before this, Server should check whether the username and password String are all legal first
	 * @param username 
	 * @param password
	 * @return 0:sign in successfully
	 *         1:has duplicated user name in database
	 *         2:disconnected with database
	 *         3:other problem
	 * 
	 */
	public int userSignIn(String username, String password);
	
	/**
	 * log in for users, check the username and password
	 * Before this, Server should check whether the user is logged in first
	 * @param username_or_userId
	 * @param password
	 * @return 0: successful
	 *         1: failed because of wrong username/password
	 *         2: other problem
	 */
	public int userLogIn(String username_or_userId, String password);
	
	
	/**
	 * save the strategic layout to the slots,
	 * each slot is a table in database: the username/userid is the primary key, and each ship position takes one column.
	 * Before use this method, Server should check whether the ShipPosition[] is overlapped first
	 * @param positions positions of the ships
	 * @param slot choose one slot to store the positions of ships
	 * @return
	 */
	public int saveShipPosition(ShipPosition[] positions, int slot);
	
	
	/**
	 * load the ship Position from database
	 * @param slot 
	 * @param username_or_userId 
	 * @return ship positions
	 */
	public ShipPosition[] loadShipPosition(String username_or_userId, int slot);
	
}
