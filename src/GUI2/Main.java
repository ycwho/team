package GUI2;

package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		 Parent root = FXMLLoader.load(getClass().getResource("gameFX.fxml"));
		 GameController gc = new GameController();
		 gc.setMain(this);
		 gc.setName("Player");
		 gc.setGrid((GridPane)root);
		 gc.hit("Player", 10, true);
		 //gc.broadcast("Enemy has disconnected");
		 Scene scene = new Scene(root);
		 primaryStage.setScene(scene);
		 primaryStage.setTitle(gc.getName());
		 primaryStage.show();

		 Parent root2 = FXMLLoader.load(getClass().getResource("gameFX.fxml"));
		 GameController gc2 = new GameController();
		 gc2.setMain(this);
		 gc2.setName("Enemy");
		 gc2.setGrid((GridPane)root2);
		 gc2.hit("Player", 10, true);
		 //gc.broadcast("Enemy has disconnected");
		 Scene scene2 = new Scene(root2);
		 Stage stage = new Stage();
		 stage.setScene(scene2);
		 stage.setTitle(gc2.getName());
		 stage.show();

		 Parent root3 = FXMLLoader.load(getClass().getResource("gameFX.fxml"));
		 GameController gc3 = new GameController();
		 gc3.setMain(this);
		 gc3.setName("Enemy2");
		 gc3.setGrid((GridPane)root3);
		 gc3.hit("Enemy", 10, true);
		 //gc.broadcast("Enemy has disconnected");
		 Scene scene3 = new Scene(root3);
		 Stage stage3 = new Stage();
		 stage3.setScene(scene3);
		 stage3.setTitle(gc3.getName());
		 stage3.show();
	}
	public static void main(String[] args) throws IOException {
		//GameController.setName("name");
		//GameController.hit("name", 10, false);
		launch(args);
		int k = 0;
		//while(k<200){
			//System.out.println("@FXML Rectangle rectangle" + k + ";");
			//k++;
		//}
		for(int i = 0; i < 10; i++){
			for(int j = 0; j< 10; j++){
				//System.out.println("<Rectangle id=\"rectangle"+k+"\" fx:id=\"rectangle"+k+"\" arcHeight=\"5.0\" arcWidth=\"5.0\" fill=\"DODGERBLUE\" height=\"50.0\" layoutX=\"10.0\" layoutY=\"40.0\" onMouseClicked=\"#shoot\" stroke=\"BLACK\" strokeType=\"INSIDE\" width=\"50.0\" GridPane.columnIndex=\"" + i + "\" GridPane.rowIndex=\"" + j +"\" />");
				k++;
			}
		}
	}
}
