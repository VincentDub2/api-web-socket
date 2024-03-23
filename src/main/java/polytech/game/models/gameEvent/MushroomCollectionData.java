package polytech.game.models.gameEvent;

public class MushroomCollectionData {
    private int id;

    private boolean isCoinCollected;

    public MushroomCollectionData(int mushroomId, boolean isMushroomCollected) {
        this.id = mushroomId;
        this.isCoinCollected = isMushroomCollected;
    }

    // Getters
    public int getId() {
        return id;
    }

    public boolean getIsCoinCollected() {
        return isCoinCollected;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setIsCoinCollected(boolean isCoinCollected) {
        this.isCoinCollected = isCoinCollected;
    }
}
