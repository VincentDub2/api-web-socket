package polytech.game.game.models.gameEvent;

public class MovementData {
    private String carId;
    private int newX;
    private int newY;
    private int newScore;

    public MovementData(String carId, int newX, int newY, int newScore) {
        this.carId = carId;
        this.newX = newX;
        this.newY = newY;
        this.newScore = newScore;
    }

    // Getters
    public String getCarId() {
        return carId;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    public int getNewScore() {
        return newScore;
    }

    // Setters

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }

}