package polytech.game.models.gameEvent;

public class CoinCollectionEvent extends GameEventMessage {
    public CoinCollectionEvent(CoinCollectionData data) {
        super("coinCollection", data);
    }
}

