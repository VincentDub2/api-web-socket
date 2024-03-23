package polytech.game.models.gameEvent;

public class MushroomAddData {
    private int x;

    private int y;

    private int id;

    public MushroomAddData(int mushroomId, int x, int y) {
        this.id = mushroomId;
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
