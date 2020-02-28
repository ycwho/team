package database;
/**
 * 
 */

/**
 * @author vini
 *
 */
public class Ship {
	private int ship1;
	private int ship2;
	private int ship3;

	public Ship(int ship1, int ship2, int ship3) {
		this.ship1 = ship1;
		this.ship2 = ship2;
		this.ship3 = ship3;
	}

	public int getShip1() {
		return ship1;
	}

	public void setShip1(int ship1) {
		this.ship1 = ship1;
	}

	public int getShip2() {
		return ship2;
	}

	public void setShip2(int ship2) {
		this.ship2 = ship2;
	}

	public int getShip3() {
		return ship3;
	}

	public void setShip3(int ship3) {
		this.ship3 = ship3;
	}

}
