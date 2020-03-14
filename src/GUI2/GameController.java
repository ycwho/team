package GUI2;

package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameController {
private String name;
//private Client client;
private Main main;
private static String[] names = new String[4];
public static ArrayList<GridPane> panes = new ArrayList<GridPane>();
@FXML private static GridPane grid;
@FXML Rectangle rectangle0;
@FXML Rectangle rectangle1;
@FXML Rectangle rectangle2;
@FXML Rectangle rectangle3;
@FXML Rectangle rectangle4;
@FXML Rectangle rectangle5;
@FXML Rectangle rectangle6;
@FXML Rectangle rectangle7;
@FXML Rectangle rectangle8;
@FXML Rectangle rectangle9;
@FXML Rectangle rectangle10;
@FXML Rectangle rectangle11;
@FXML Rectangle rectangle12;
@FXML Rectangle rectangle13;
@FXML Rectangle rectangle14;
@FXML Rectangle rectangle15;
@FXML Rectangle rectangle16;
@FXML Rectangle rectangle17;
@FXML Rectangle rectangle18;
@FXML Rectangle rectangle19;
@FXML Rectangle rectangle20;
@FXML Rectangle rectangle21;
@FXML Rectangle rectangle22;
@FXML Rectangle rectangle23;
@FXML Rectangle rectangle24;
@FXML Rectangle rectangle25;
@FXML Rectangle rectangle26;
@FXML Rectangle rectangle27;
@FXML Rectangle rectangle28;
@FXML Rectangle rectangle29;
@FXML Rectangle rectangle30;
@FXML Rectangle rectangle31;
@FXML Rectangle rectangle32;
@FXML Rectangle rectangle33;
@FXML Rectangle rectangle34;
@FXML Rectangle rectangle35;
@FXML Rectangle rectangle36;
@FXML Rectangle rectangle37;
@FXML Rectangle rectangle38;
@FXML Rectangle rectangle39;
@FXML Rectangle rectangle40;
@FXML Rectangle rectangle41;
@FXML Rectangle rectangle42;
@FXML Rectangle rectangle43;
@FXML Rectangle rectangle44;
@FXML Rectangle rectangle45;
@FXML Rectangle rectangle46;
@FXML Rectangle rectangle47;
@FXML Rectangle rectangle48;
@FXML Rectangle rectangle49;
@FXML Rectangle rectangle50;
@FXML Rectangle rectangle51;
@FXML Rectangle rectangle52;
@FXML Rectangle rectangle53;
@FXML Rectangle rectangle54;
@FXML Rectangle rectangle55;
@FXML Rectangle rectangle56;
@FXML Rectangle rectangle57;
@FXML Rectangle rectangle58;
@FXML Rectangle rectangle59;
@FXML Rectangle rectangle60;
@FXML Rectangle rectangle61;
@FXML Rectangle rectangle62;
@FXML Rectangle rectangle63;
@FXML Rectangle rectangle64;
@FXML Rectangle rectangle65;
@FXML Rectangle rectangle66;
@FXML Rectangle rectangle67;
@FXML Rectangle rectangle68;
@FXML Rectangle rectangle69;
@FXML Rectangle rectangle70;
@FXML Rectangle rectangle71;
@FXML Rectangle rectangle72;
@FXML Rectangle rectangle73;
@FXML Rectangle rectangle74;
@FXML Rectangle rectangle75;
@FXML Rectangle rectangle76;
@FXML Rectangle rectangle77;
@FXML Rectangle rectangle78;
@FXML Rectangle rectangle79;
@FXML Rectangle rectangle80;
@FXML Rectangle rectangle81;
@FXML Rectangle rectangle82;
@FXML Rectangle rectangle83;
@FXML Rectangle rectangle84;
@FXML Rectangle rectangle85;
@FXML Rectangle rectangle86;
@FXML Rectangle rectangle87;
@FXML Rectangle rectangle88;
@FXML Rectangle rectangle89;
@FXML Rectangle rectangle90;
@FXML Rectangle rectangle91;
@FXML Rectangle rectangle92;
@FXML Rectangle rectangle93;
@FXML Rectangle rectangle94;
@FXML Rectangle rectangle95;
@FXML Rectangle rectangle96;
@FXML Rectangle rectangle97;
@FXML Rectangle rectangle98;
@FXML Rectangle rectangle99;


 public void shoot(MouseEvent e){
	 Node source = (Node)e.getSource();
	 grid = (GridPane) source.getParent();
	 System.out.println(grid.getChildren().indexOf(source) + " " + names[panes.indexOf((GridPane)source.getParent())]);
	 Rectangle rect = (Rectangle)source;
	 rect.setFill(Color.BLACK);
	 //client.write(Protocol.CLIENT_ATTACK + " " + names[panes.indexOf((GridPane)source.getParent())] + " " + position);
 }
 public GameController(){
 }
 public void broadcast(String message){
     Text text = new Text(message);
     text.setX((message.length())*15);
     text.setY(50);
     text.setScaleX(5);
     text.setScaleY(5);
     Group group = new Group(text);
     Stage stage = new Stage();
     stage.setScene(new Scene(group));
     stage.sizeToScene();
     stage.show();
 }
 public void setNames (String[] newNames){
	 names = newNames;
 }

 public void hit (String newName, int position, boolean hit) {
	 int k = 0;
	 while(names[k] != newName){
		 k++;
	 }
	 if(!hit){
		 Node node = panes.get(k).getChildren().get(position);
		 Rectangle rect = (Rectangle)node;
		 rect.setFill(Color.BLACK);
	 }else if(hit){
		 Node node = panes.get(k).getChildren().get(position);
		 Rectangle rect = (Rectangle)node;
		 rect.setFill(Color.ORANGE);
	 }
 }
 public void setName(String newName){
	 name = newName;
 }
 public String getName(){
	 return name;
 }
 public void setGrid(GridPane pane){
	 grid = pane;
 }
 //public void setClient(Client newClient){
 //	 client = newClient;
 //}
 public void setMain(Main newMain){
	 main = newMain;
 }
 public GridPane getGrid(){
	 return grid;
 }

}

