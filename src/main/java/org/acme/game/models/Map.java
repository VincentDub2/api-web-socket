package org.acme.game.models;
import java.util.List;
public class Map {
    private List<Wall> walls;
    private List<Coin> coins;

    public Map(List<Wall> walls, List<Coin> coins) {
        this.walls = walls;
        this.coins = coins;
    }

}
