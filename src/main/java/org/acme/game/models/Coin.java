package org.acme.game.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;

public class Coin {
    private static int idCounter = 0;
    private int id;
    private int x;
    private int y;

    private final int width = 2;

    private final int height = 2;


    private boolean collected;

    public Coin(int x, int y) {
        this.id = ++idCounter;
        this.x = x;
        this.y = y;
        this.collected = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public Rectangle getHitbox() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
    public boolean isCollected() {
        return collected;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
