package GUI;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Random;

/**
 * Main class for the GUI
 * @author ELizabeth Atkins
 * */
public class GUIMain extends Application{
	private static Board playerBoard;
	private static Board enemyBoard;
	private static boolean playerTurn;
	private static Text placedShips; //this set as a field variable so it can be accessed by both methods
	public static void main(String[] args) {
		setUp();
		launch(args);
	}
	public static void setUp() {
		//enables the player to place ships
		playerTurn = true;
		placedShips = new Text("Ships placed: 0");
		 playerBoard = new Board(false, event -> {
	           Board.Cell cell = (Board.Cell) event.getSource();
	           //playerBoard.setShipLength(4);
	           playerBoard.placeShip(playerBoard.getCells().indexOf(cell));
	           placedShips.setText("Ships placed: " + playerBoard.getShips());
	           });
		//enables the player to strike the enemy board
		enemyBoard = new Board(true, event ->  {
				Board.Cell cell = (Board.Cell) event.getSource();
				if(playerBoard.getShips() == 5) {
				cell.strike();
				if(cell.isHit()) {
					playerTurn = false;
				}
				if(!playerTurn) {
	   				simulateEnemy();
	   			}
				}
			});
			//Having "enemy" place random ships for testing purposes
			Random rand = new Random();
			enemyBoard.setShipLength(4);
			for(int i = 0; i < 5;i++) {
				enemyBoard.placeShip(rand.nextInt(100));
			}
	}
	/**
	 * Method to simulate an enemy for testing purposes
	 * */
	private static void simulateEnemy() {
		while(!playerTurn) {
			Random r = new Random();
			Board.Cell cell = playerBoard.getCell(r.nextInt(100));
			cell.strike();
			if(cell.isHit()) {
				playerTurn = true;
			}
		}
	}
	/**
	 * The enemy board and the player board come up in two different windows that annoyingly load
	 * on top of each other
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Group group = new Group();


		for(Board.Cell c : playerBoard.getCells()) {
			group.getChildren().add(c);
		}
		Text text = new Text("Press H to set ships horizontal, press V to set ships vertical. \n" +
		"Press up arrow to increase ship size, press down arrow to decrease ship size");
		Text text2 = new Text("Current ship length: " + playerBoard.getShipLength());
		Text text3 = new Text("Ship orientation: horizontal");
		text.setX(10);
		text.setY(520);
		group.getChildren().add(text);
		text2.setX(10);
		text2.setY(560);
		group.getChildren().add(text2);
		text3.setX(10);
		text3.setY(580);
		group.getChildren().add(text3);
		placedShips.setX(10);
		placedShips.setY(600);
		group.getChildren().add(placedShips);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Battleship - Player");
		primaryStage.show();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {

	            switch (keyEvent.getCode()) {
	                case V:
	                    playerBoard.setVertical();
	                    text3.setText("Ship orientation: vertical");
	                    break;
	                case H:
	                	playerBoard.setHorizontal();
	                	text3.setText("Ship orientation: horizontal");
	                	break;
	                case UP:
	                	playerBoard.setShipLength(playerBoard.getShipLength() + 1);
	                	text2.setText("Current ship length: " + playerBoard.getShipLength());
	                	break;
	                case DOWN:
	                	playerBoard.setShipLength(playerBoard.getShipLength() - 1);
	                	text2.setText("Current ship length: " + playerBoard.getShipLength());
	                	break;
	                	

	            }
				
			}
			
		});

		Stage secondStage = new Stage();
		Group secondGroup = new Group();
		for(Board.Cell c : enemyBoard.getCells()) {
			secondGroup.getChildren().add(c);
		}
		Scene scene2 = new Scene(secondGroup);
		secondStage.setScene(scene2);
		secondStage.setTitle("Battleship - Enemy");
		secondStage.show();
	}
}
