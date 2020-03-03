package application;

public class Controller {

    private Client client;
//todo work out host for client argument
    public Controller() {
        this.client = new Client("localhost");
    }


}
