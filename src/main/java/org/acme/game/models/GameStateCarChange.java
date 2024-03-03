package org.acme.game.models;

public class GameStateCarChange {
    private String carId;
    private int newX;
    private int newY;
    private int newScore;
    public GameStateCarChange(String carId, int newX, int newY, int newScore) {
        this.carId = carId;
        this.newX = newX;
        this.newY = newY;
        this.newScore = newScore;
    }



    public GameStateCarChange(String carId, int newX, int newY) {
        this.carId = carId;
        this.newX = newX;
        this.newY = newY;
    }

    public GameStateCarChange() {
    }

    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public int getNewScore() {
        return newScore;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }

}
