package polytech.game.models;
import java.util.List;

public record Map(List<Wall> walls, List<Coin> coins,List<Mushroom> mushrooms) {

    public boolean isWall(int x, int y) {
        for (Wall wall : walls) {
            if (wall.getX() == x && wall.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public boolean isCoin(int x, int y) {
        for (Coin coin : coins) {
            if (coin.getX() == x && coin.getY() == y && !coin.isCollected()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMushroom(int x, int y) {
        for (Mushroom mushroom : mushrooms) {
            if (mushroom.getX() == x && mushroom.getY() == y && !mushroom.isCollected()) {
                return true;
            }
        }
        return false;
    }

    public void setMushroomCollected(int x, int y, boolean collected) {
        if (collected) {
            mushrooms.removeIf(mushroom -> mushroom.getX() == x && mushroom.getY() == y && !mushroom.isCollected());
        }
    }

    public void setCoinCollected(int x, int y) {
        coins.removeIf(coin -> coin.getX() == x && coin.getY() == y);
    }

}
