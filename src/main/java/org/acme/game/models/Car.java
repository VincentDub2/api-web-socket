package org.acme.game.models;

public class Car {
    private String id;
    private int x;
    private int y;
    private int direction; // 0: north, 1: east, 2: south, 3: west
    private int score;

    public String getId() {
        return id;
    }

    public Car(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = 0; // Initial direction facing north
        this.score = 0;
    }
}
