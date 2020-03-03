import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {
	private boolean isEnemy;
	private ArrayList<Cell> cells;
	private int ships;
	private boolean isVertical;
	private int shipLength;
	Board(boolean enemy,  EventHandler<? super MouseEvent> handler){
		cells = new ArrayList<Cell>();
		isEnemy = enemy;
		for(int i = 0; i<10;i++) {
			for(int j = 0; j < 10; j++) {
				Cell c = new Cell(i*50,j*50);
				cells.add(c);
				 c.setOnMouseClicked(handler);
			}
		}
	}
	public void setVertical() {
		isVertical = true;
	}
	public void setHorizontal() {
		isVertical = false;
	}
	public ArrayList<Cell> getCells(){
		return cells;
	}
	public int getShips() {
		return ships;
	}
	public Cell getCell(int i) {
		return cells.get(i);
	}
	public void placeShip(int i) {
		int length = this.getShipLength();
		Cell cell = getCell(i);
		if(isEnemy == false && ships < 5 && isVertical == false) {
			cell.isShip = true;
			cell.setFill(Color.WHITE);
			int n = 1;
			if(i < (100 - length*10)) {
				while(n < length){
					getCell(i+10).setFill(Color.WHITE);
					getCell(i+10).isShip = true;
					i+=10;
					n++;
				}
			}else {
				while(n < length) {
					getCell(i-10).setFill(Color.WHITE);
					getCell(i-10).isShip = true;
					i-=10;
					n++;
				}
			}
		ships++;
		}else if(isEnemy == true && ships < 5 && isVertical == false){
			cell.isShip = true;
			//cell.setFill(Color.GREEN);
			int n = 1;
			if(i < (100 - length*10)) {
				while(n < length){
					//getCell(i+10).setFill(Color.GREEN);
					getCell(i+10).isShip = true;
					i+=10;
					n++;
				}
			}else {
				while(n < length) {
					//getCell(i-10).setFill(Color.GREEN);
					getCell(i-10).isShip = true;
					i-=10;
					n++;
				}
			}
			ships++;
			
		}
		if(isEnemy == false && ships < 5 && isVertical == true) {
			boolean overEdge = false;
			int n = 1;
			cell.isShip = true;
			cell.setFill(Color.WHITE);
			for(int j = i; j < (i + length); j++) {
				if(j % 10 == 0 && j!= i) {
					overEdge = true;
					j = i + length;
				}else {
					overEdge = false;
				}
			}
			if(overEdge == false) {
				while(n<length) {
				getCell(i+1).isShip = true;
				getCell(i+1).setFill(Color.WHITE);
				i++;
				n++;
				}
			}else {
				while(n<length) {
					getCell(i-1).isShip = true;
					getCell(i-1).setFill(Color.WHITE);
					i--;
					n++;
				}
			}
			ships++;
		}else if(isEnemy == true && ships < 5 && isVertical == true) {
			boolean overEdge = false;
			cell.isShip = true;
			int n = 1;
			for(int j = i; j < (i + length); j++) {
				if(j % 10 == 0 && j!= i) {
					overEdge = true;
					j = i + length;
				}else {
					overEdge = false;
				}
			}
			if(overEdge == false) {
				while(n<length) {
				getCell(i+1).isShip = true;
				i++;
				n++;
				}
			}else {
				while(n<length) {
					getCell(i-1).isShip = true;
					i--;
					n++;
				}
			}
		}
	}
	public int getShipLength() {
		return shipLength;
	}
	public void setShipLength(int shipLength) {
		this.shipLength = shipLength;
	}
	public class Cell extends Rectangle{
		private boolean hit;
		private boolean isShip;
		Cell(int x,int y){
			super(50,50);
			this.setFill(Color.LIGHTSKYBLUE);
			this.setStroke(Color.BLACK);
			hit = false;
			isShip = false;
			this.setX(x);
			this.setY(y);
		}
		public void strike() {
			if(isShip == false) {
				hit = true;
				this.setFill(Color.BLACK);
			}else if(isShip == true){
				hit = true;
				this.setFill(Color.ORANGE);
			}else {
				
			}
		}
		public boolean isHit() {
			return hit;
		}
		public int getIndex() {
			if(cells.contains(this)) {
				return cells.indexOf(this);
			}else {
				throw new IllegalArgumentException();
			}
		}
	}
}
