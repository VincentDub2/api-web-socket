package polytech.game.models.gameEvent;

public class CoinCollectionData {
    private int id;

    private boolean isCoinCollected;

    public CoinCollectionData(int coinId, boolean isCoinCollected) {
        this.id = coinId;
        this.isCoinCollected = isCoinCollected;
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