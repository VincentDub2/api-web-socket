package org.acme.game.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;

public class Car {
    private String id;
    private int x;
    private int y;

    private int speed;
    private int direction; // 0: north, 1: east, 2: south, 3: west
    private int score;
    private int width;
    private int height;

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

    @JsonIgnore
    public Rectangle getHitbox() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Car(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.speed = 20; // Initial speed
        this.direction = 0; // Initial direction facing north
        this.score = 0;
        this.width = 30;
        this.height = 15;
    }
}
