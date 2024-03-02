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
}
