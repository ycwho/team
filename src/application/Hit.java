package application;

public class Hit {

    private boolean isHit;
    private boolean isSunk;

    public Hit (boolean isHit, boolean isSunk) {
        this.isHit = isHit;
        this.isSunk = isSunk;
    }

    public boolean getIsHit() {
        return this.isHit;
    }

    public boolean getIsSunk() {
        return this.isSunk;
    }


}
