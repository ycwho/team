package GUI2;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Main extends Application {
	private String[] names;
	private String username;
	@Override
	public void start(Stage primaryStage) throws IOException {
		//String[] names = {"Player","Walter","Bob"};
		GameController gc = new GameController();
		gc.panes = new ArrayList<GridPane>();
		for(String s : names){
			Parent root = FXMLLoader.load(getClass().getResource("gameFX.fxml"));
			gc.setMain(this);
			//gc.setClient(client);
			gc.setUserName(username);
			System.out.println(username);
			gc.setGrid((GridPane)root);
			gc.getPanes().add(gc.getGrid());
			gc.setNames(names);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(s);
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
	public Main(String[] newNames, String username){
		this.names = newNames;
		this.username = username;
	}
}
