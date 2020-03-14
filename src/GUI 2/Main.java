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
		 FXMLLoader loader = FXMLLoader.load(getClass().getResource("aaaaaaaaaa.fxml"));
		 Parent root = loader.load();
		 GameController gc = loader.getController();
		 Scene scene = new Scene(root);
		 primaryStage.setScene(scene);
		 primaryStage.show();
	}

	public static void main(String[] args) {
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