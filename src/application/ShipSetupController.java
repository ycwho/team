package application;

import java.io.IOException;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ShipSetupController {
	
	@FXML
	private Button saveButton;
	@FXML
	private Button backButton;
	@FXML
	private TextField name;
	@FXML
	private GridPane playersGrid;
	@FXML
	private Button p00;
	@FXML
	private Button p01;
	@FXML
	private Button p02;
	@FXML
	private Button p03;
	@FXML
	private Button p04;
	@FXML
	private Button p05;
	@FXML
	private Button p06;
	@FXML
	private Button p07;
	@FXML
	private Button p08;
	@FXML
	private Button p09;
	@FXML
	private Button p10;
@FXML
	private Button p11;
@FXML
	private Button p12;
@FXML
	private Button p13;
@FXML
	private Button p14;
@FXML
	private Button p15;
@FXML
	private Button p16;
@FXML
	private Button p17;
@FXML
	private Button p18;
@FXML
	private Button p19;
@FXML
	private Button p20;
@FXML
	private Button p21;
@FXML
	private Button p22;
@FXML
	private Button p23;
@FXML
	private Button p24;
@FXML
	private Button p25;
@FXML
	private Button p26;
@FXML
	private Button p27;
@FXML
	private Button p28;
@FXML
	private Button p29;
@FXML
	private Button p30;
@FXML
	private Button p31;
@FXML
	private Button p32;
@FXML
	private Button p33;
@FXML
	private Button p34;
@FXML
	private Button p35;
@FXML
	private Button p36;
@FXML
	private Button p37;
@FXML
	private Button p38;
@FXML
	private Button p39;
@FXML
	private Button p40;
@FXML
	private Button p41;
@FXML
	private Button p42;
@FXML
	private Button p43;
@FXML
	private Button p44;
@FXML
	private Button p45;
@FXML
	private Button p46;
@FXML
	private Button p47;
@FXML
	private Button p48;
@FXML
	private Button p49;
@FXML
	private Button p50;
@FXML
	private Button p51;
@FXML
	private Button p52;
@FXML
	private Button p53;
@FXML
	private Button p54;
@FXML
	private Button p55;
@FXML
	private Button p56;
@FXML
	private Button p57;
@FXML
	private Button p58;
@FXML
	private Button p59;
@FXML
	private Button p60;
@FXML
	private Button p61;
@FXML
	private Button p62;
@FXML
	private Button p63;
@FXML
	private Button p64;
@FXML
	private Button p65;
@FXML
	private Button p66;
@FXML
	private Button p67;
@FXML
	private Button p68;
@FXML
	private Button p69;
@FXML
	private Button p70;
@FXML
	private Button p71;
@FXML
	private Button p72;
@FXML
	private Button p73;
@FXML
	private Button p74;
@FXML
	private Button p75;
@FXML
	private Button p76;
@FXML
	private Button p77;
@FXML
	private Button p78;
@FXML
	private Button p79;
@FXML
	private Button p80;
@FXML
	private Button p81;
@FXML
	private Button p82;
@FXML
	private Button p83;
@FXML
	private Button p84;
@FXML
	private Button p85;
@FXML
	private Button p86;
	@FXML
	private Button p87;
	@FXML
	private Button p88;
	@FXML
	private Button p89;
	@FXML
	private Button p90;
@FXML
	private Button p91;
	@FXML
	private Button p92;
	@FXML
	private Button p93;
	@FXML
	private Button p94;
	@FXML
	private Button p95;
	@FXML
	private Button p96;
	@FXML
	private Button p97;
	@FXML
	private Button p98;
	@FXML
	private Button p99;
	@FXML
	private TextArea instructions;
	private int placed5 = 0;
	private int placed4 = 0;
	private int placed3a = 0;
	private int placed3b = 0;
	private static int placed2 = 0;
	private String shipPositions = "";
	private int lastPos;
	private Client client;
	private Main main;
	
	
	public int toPos(String coords) {
		String[] xy = coords.split(",");
		int pos = Integer.parseInt(xy[1]) + Integer.parseInt(xy[0])*10;
		return pos;
	}
	
	@FXML
	protected void attack(MouseEvent event) throws IOException {
		System.out.println("pressed");
		Button button = (Button) event.getSource();
		int pos = toPos(button.getText());
		System.out.println(pos);
		if(button.getStyle().equals("-fx-background-color: null;")) {
			if(placed5 == 0) {
				button.setStyle("-fx-background-color: #00ff00");
				//on first row grod positions is pos-1
				placed5 = 1;
				shipPositions =  shipPositions + String.valueOf(pos);
				lastPos = pos;
				instructions.setText("Place the tail of your first ship 4 away.");
			}
			else if(placed5 == 1) {
				if(pos == lastPos + 40 && playersGrid.getChildren().get(pos-30).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos-20).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos-10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" + String.valueOf(pos-30);
					shipPositions =  shipPositions + "@" +String.valueOf(pos-20);
					shipPositions =  shipPositions + "@" +String.valueOf(pos-10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos) +"-";
					playersGrid.getChildren().get(pos-30).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos-20).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos-10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 4.");
					placed5 = 2;
				}
				if(pos == lastPos - 40 && playersGrid.getChildren().get(pos+30).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos+20).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos+10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos+30);
					shipPositions =  shipPositions + "@" +String.valueOf(pos+20);
					shipPositions =  shipPositions + "@" +String.valueOf(pos+10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+30).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos+20).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos+10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 4.");
					placed5 = 2;
					System.out.println(pos + " " + lastPos);
				}
				if(pos-4 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos-3).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos-2).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos-1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos-3);
					shipPositions =  shipPositions +"@" + String.valueOf(pos-2);
					shipPositions =  shipPositions +"@" + String.valueOf(pos-1);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos-3).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos-2).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos-1).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(lastPos).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 4.");
					placed5 = 2;
				}
				if(pos+4 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos+3).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos+2).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos+1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos+3);
					shipPositions =  shipPositions +"@" + String.valueOf(pos+2);
					shipPositions =  shipPositions + "@" +String.valueOf(pos+1);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+3).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos+2).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos+1).setStyle("-fx-background-color: #00ff00");
					
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 4.");
					placed5 = 2;
				}
			}
			else if(placed4==0) {
				button.setStyle("-fx-background-color: #00ff00");
				shipPositions =  shipPositions + String.valueOf(pos);
				placed4 = 1;
				lastPos = pos;
				instructions.setText("Place the tail of your ship 3 away.");
			}
			else if (placed4==1) {
				if(pos == lastPos + 30 && playersGrid.getChildren().get(pos-20).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos-10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos-20);
					shipPositions =  shipPositions + "@" +String.valueOf(pos-10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos) +"-";
					playersGrid.getChildren().get(pos-20).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos-10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed4=2;
				}
				if(pos == lastPos - 30 && playersGrid.getChildren().get(pos+20).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos+10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions +"@" + String.valueOf(pos+20);
					shipPositions =  shipPositions +"@" + String.valueOf(pos+10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+20).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos+10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed4=2;
				}
				if(pos-3 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos-2).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos-1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos-2);
					shipPositions =  shipPositions +"@" + String.valueOf(pos-1);
					shipPositions =  shipPositions +"@" + String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos-2).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos-1).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed4=2;
				}
				if(pos+3 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos+2).getStyle().equals("-fx-background-color: null;") && playersGrid.getChildren().get(pos+1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos+2);
					shipPositions =  shipPositions + "@" +String.valueOf(pos+1);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+2).setStyle("-fx-background-color: #00ff00");
					playersGrid.getChildren().get(pos+1).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed4=2;
				}
			}
			else if(placed3a==0) {
				button.setStyle("-fx-background-color: #00ff00");
				shipPositions =  shipPositions + String.valueOf(pos);
				placed3a = 1;
				lastPos = pos;
				instructions.setText("Place the tail of your ship 2 away.");
			}
			else if (placed3a==1) {
				if(pos == lastPos + 20 && playersGrid.getChildren().get(pos-10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos-10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos) +"-";
					playersGrid.getChildren().get(pos-10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed3a=2;
				}
				if(pos == lastPos - 20 && playersGrid.getChildren().get(pos+10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos+10);
					shipPositions =  shipPositions +"@" + String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed3a=2;
				}
				if(pos-2 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos-1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions +"@" + String.valueOf(pos-1);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos-1).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed3a=2;
				}
				if(pos+2 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos+1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos+1);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+1).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 3.");
					placed3a=2;
				}
			}
			else if(placed3b==0) {
				button.setStyle("-fx-background-color: #00ff00");
				shipPositions =  shipPositions + String.valueOf(pos);
				placed3b = 1;
				lastPos = pos;
				instructions.setText("Place the tail of your ship 2 away.");
			}
			else if (placed3b==1) {
				if(pos == lastPos + 20 && playersGrid.getChildren().get(pos-10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos-10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos) +"-";
					playersGrid.getChildren().get(pos-10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 2.");
					placed3b=2;
				}
				if(pos == lastPos - 20 && playersGrid.getChildren().get(pos+10).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos+10);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+10).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 2.");
					placed3b=2;
				}
				if(pos-2 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos-1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos-1);
					shipPositions =  shipPositions + "@" +String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos-1).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 2.");
					placed3b=2;
				}
				if(pos+2 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10) && playersGrid.getChildren().get(pos+1).getStyle().equals("-fx-background-color: null;")) {
					shipPositions =  shipPositions +"@" + String.valueOf(pos+1);
					shipPositions =  shipPositions +"@" + String.valueOf(pos)+"-";
					playersGrid.getChildren().get(pos+1).setStyle("-fx-background-color: #00ff00");
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Place the head of your next ship of length 2.");
					placed3b=2;
				}
			}
			else if(placed2==0) {
				button.setStyle("-fx-background-color: #00ff00");
				shipPositions =  shipPositions + String.valueOf(pos);
				placed2 = 1;
				lastPos = pos;
				instructions.setText("Place the tail of your ship 2 away.");
			}
			else if (placed2==1) {
				if(pos == lastPos + 10) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos);
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Please save your ship locations with a name for it.");
					placed2=2;
				}
				if(pos == lastPos - 10) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos);
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Please save your ship locations with a name for it.");
					placed2=2;
				}
				if(pos-1 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10)) {
					shipPositions =  shipPositions + "@" +String.valueOf(pos);
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Please save your ship locations with a name for it.");
					placed2=2;
				}
				if(pos+1 == lastPos && Math.floorDiv(pos, 10) == Math.floorDiv(lastPos, 10)) {
					shipPositions =  shipPositions +"@" + String.valueOf(pos);
					button.setStyle("-fx-background-color: #00ff00");
					instructions.setText("Please save your ship locations with a name for it.");
					placed2=2;
				}
			}
		}
		
	
	}
	
	@FXML
	protected void save(MouseEvent event) throws IOException {
		if(!name.getText().isEmpty()) {
			String write = Protocol.CLIENT_UPLOAD_SHIP_POSITIONS + " "+ name.getText() + " " + shipPositions;
			System.out.println(write);
			client.write(write);
		}
	}
	
	
	
	@FXML
	protected void back(MouseEvent event) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					main.setMainMenuStage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}


	public void setClient(Client client) {
		// TODO Auto-generated method stub
		this.client = client;
	}

	public void setMain(Main main) {
		// TODO Auto-generated method stub
		this.main = main;
	}

	

}
