package application;

import javafx.stage.Stage;

public class GameController {

//Methods to send to server:
    // 1) Protocol.CLIENT_ATTACK; - the game waits for an attack - so when attacking send a string of Protocol.CLIENT_ATTACK + " " + playerName + " " + position
    // 2) Protocol.CLIENT_UPLOAD_SHIP_POSITIONS; - to upload the positions, send the string to the server: Protocol.CLIENT_UPLOAD_SHIP_POSITIONS + "," + playerName + ",s1p1 s1p2 s1p3 s1p4 s1p5,s2p1 s2p2 etc.........
    // 3) Protocol.CLIENT_QUIT_GAME; - when a player leaves the game send the string Protocol.CLIENT_QUIT_GAME
    // 4) Protocol.PLAYER_NAMES_REQUEST; - will need the names of the players to display and also to make any attack, so will need to send the string of this protocol


//Methods to receive from server (client will take a string and call a method of the following format):

        //public Game(String ownPlayerName, String[] Player_Names){
                //constructor for the game so that it knows how many players and who is who
        //}

        public void broadcast(String message){
            //display a message for the player - this will have stuff like player disconnected, if its your turn, etc etc
        }

        public void setNames (String[] names){
            // will set a field variable in to game to hold the names of the players
        }

        public void hit (String name, int position, boolean hit) {
            // will update your own grid if your name is the same, and or your corresponding enemies name if it's their name
        }


        // maybe a way to handle a player logging out? at the moment just writes a message and nothing else - unsure what this will look like























//    //todo stub
//    private Client client;
//    private Main main;
//    public Stage primaryStage;
//
//    public Client getClient() {
//        return client;
//    }
//    public void setClient(Client client) {
//        this.client = client;
//    }
//    public Main getMain() {
//        return main;
//    }
//    public void setMain(Main main) {
//        this.main = main;
//    }










}
