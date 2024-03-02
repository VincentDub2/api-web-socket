package org.acme.game.models;

public class Car {
    private String id;
    private int x;
    private int y;

    private int speed;
    private int direction; // 0: north, 1: east, 2: south, 3: west
    private int score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public Car(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.speed = 1; // Initial speed
        this.direction = 0; // Initial direction facing north
        this.score = 0;
    }
}
