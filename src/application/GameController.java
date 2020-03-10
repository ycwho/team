package application;

import javafx.stage.Stage;

public class GameController {

    //todo stub
    private Client client;
    private Main main;
    public Stage primaryStage;

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Main getMain() {
        return main;
    }
    public void setMain(Main main) {
        this.main = main;
    }

    public Hit attack(int position) {


        boolean isHit = true;
        boolean isSunk = true;
        Hit hit = new Hit(isHit,isSunk);

        return hit;
    }




}
