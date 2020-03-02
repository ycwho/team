import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;

import java.util.Random;
/**
 * Main class for the GUI
 * @author ELizabeth Atkins
 * Improvements that need to be made:
 * I need to have it so you can change the ship length
 * */
public class GUIMain extends Application{
	private static Board playerBoard;
	private static Board enemyBoard;
	private static boolean playerTurn;
	public static void main(String[] args) {
		setUp();
		launch(args);
	}
	public static void setUp() {
		//enables the player to place ships
		playerTurn = true;
		 playerBoard = new Board(false, event -> {
	           Board.Cell cell = (Board.Cell) event.getSource();
	           playerBoard.placeShip(playerBoard.getCells().indexOf(cell),4);
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
			for(int i = 0; i < 5;i++) {
				enemyBoard.placeShip(rand.nextInt(100), 4);
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
		Text text = new Text("Press H to set ships horizontal, press V to set ships vertical" );
		//Text secondLine = new Text("Ships placed: " + playerBoard.getShips());
		text.setX(10);
		text.setY(520);
		//secondLine.setX(10);
		//secondLine.setY(530);
		//group.getChildren().add(secondLine);
		group.getChildren().add(text);
		//group.getChildren().add(secondLine);
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
	                    break;
	                case H:
	                	playerBoard.setHorizontal();

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
