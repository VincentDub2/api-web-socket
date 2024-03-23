package polytech.game.models.gameEvent;

public class CoinAddData {
    private int id;

    private int x;

    private int y;

    public CoinAddData(int coinId, int x, int y) {
        this.id = coinId;
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
