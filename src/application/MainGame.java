package application;

import java.io.IOException;
import java.util.ArrayList;
import application.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class MainGame extends Application {
	private String[] names;
	private String username;
	private Client client;
	private Main main;
	private ArrayList<Integer> personalShips;
	@Override
	public void start(Stage primaryStage) throws IOException {
		//String[] names = {"Player","Walter","Bob"};
		GameController gc = new GameController();
		gc.setClient(client);
		gc.panes = new ArrayList<GridPane>();
		for(String s : names){
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameFX2.fxml"));
			Parent root = fxmlLoader.load();
			gc = fxmlLoader.getController();
			client.setGameController(gc);
			gc.setMainGame(this);
			gc.setClient(client);
			gc.setMain(main);
			gc.setUserName(username);
			gc.setGrid((GridPane)root);
			gc.getPanes().add(gc.getGrid());
			gc.setNames(names);
			gc.setUp(personalShips);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			if(s.equalsIgnoreCase(username)) {
				stage.setTitle("Player " + username + ": Your board");
			}
			else{
				stage.setTitle("Player " + username + ": enemy " +s+ "'s board");
			}
			stage.show();
		}
	}
	public static void main(String[] args) throws IOException {
		//GameController.setName("name");
		//GameController.hit("name", 10, false);
		launch(args);
		//int k = 0;
		//while(k<200){
			//System.out.println("@FXML Rectangle rectangle" + k + ";");
			//k++;
		//}
		//for(int i = 0; i < 10; i++){
		//	for(int j = 0; j< 10; j++){
				//System.out.println("<Rectangle id=\"rectangle"+k+"\" fx:id=\"rectangle"+k+"\" arcHeight=\"5.0\" arcWidth=\"5.0\" fill=\"DODGERBLUE\" height=\"50.0\" layoutX=\"10.0\" layoutY=\"40.0\" onMouseClicked=\"#shoot\" stroke=\"BLACK\" strokeType=\"INSIDE\" width=\"50.0\" GridPane.columnIndex=\"" + i + "\" GridPane.rowIndex=\"" + j +"\" />");
				//k++;
		//	}
		//}
	}
	public MainGame(String[] newNames, Client client, String userShips, Main main){
		this.main = main;
		this.client = client;
		this.names = newNames;
		this.username = client.getUsername();
        String[] first = userShips.split("-");
        personalShips = new ArrayList<Integer>();
		for(String s : first){
			String[] second = s.split("@"); //maybe need space back was ". " before
			for(String s2 : second){
				personalShips.add(Integer.parseInt(s2));
			}
		}

	}
}
