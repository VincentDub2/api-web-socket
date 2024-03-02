package org.acme.game.models;

public class Coin {
    private int x;
    private int y;
    private boolean collected;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        this.collected = false;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
    public boolean isCollected() {
        return collected;
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
