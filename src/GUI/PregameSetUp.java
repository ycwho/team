package GUI;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PregameSetUp extends Application{
	private static Board board;
	private static Text text;
	private static ArrayList<Integer> shipPositions;
	private static Client client;
	public static void setUp() {
		text = new Text("Ships placed: 0");
		shipPositions = new ArrayList<Integer>();
		board = new Board(false, event -> {
			if(board.getShips() == 0) {
				board.setShipLength(5);
			}else if(board.getShips() == 1) {
				board.setShipLength(4);
			}else if(board.getShips() == 2 || board.getShips() == 3) {
				board.setShipLength(3);
			}else {
				board.setShipLength(2);
			}
			Board.Cell cell = (Board.Cell) event.getSource();
			board.placeShip(board.getCells().indexOf(cell));
			text.setText("Ships placed: " + board.getShips());
		});
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Ship Setup");
		Group group = new Group();
		for(Board.Cell c : board.getCells()) {
			group.getChildren().add(c);
		}
		group.getChildren().add(text);
		text.setX(10);
		text.setY(520);
		Scene scene = new Scene(group);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {

	            switch (keyEvent.getCode()) {
	                case V:
	                    board.setVertical();
	                    //text3.setText("Ship orientation: vertical");
	                    break;
	                case H:
	                	board.setHorizontal();
	                	//text3.setText("Ship orientation: horizontal");
	                	break;
	            }
				
			}
			
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		setUp();
		launch(args);
		for(Board.Cell c : board.getCells()) {
			if(c.isShip()) {
				shipPositions.add(board.getCells().indexOf(c));
			}
		}
		System.out.println(shipPositions);
	}
	public PregameSetUp(Client client) {
		this.client = client;
	}

}
