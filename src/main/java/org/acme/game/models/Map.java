package org.acme.game.models;
import java.util.List;
public class Map {
    private List<Wall> walls;
    private List<Coin> coins;

    public Map(List<Wall> walls, List<Coin> coins) {
        this.walls = walls;
        this.coins = coins;
    }

    public boolean isWall(int x, int y) {
        for (Wall wall : walls) {
            if (wall.getX() == x && wall.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public boolean isCoin(int x, int y) {
        for (Coin coin : coins) {
            if (coin.getX() == x && coin.getY() == y && !coin.isCollected()) {
                return true;
            }
        }
        return false;
    }

    public void setCoinCollected(int x, int y, boolean collected) {
        for (Coin coin : coins) {
            if (coin.getX() == x && coin.getY() == y) {
                coin.setCollected(collected);
            }
        }
    }

}
