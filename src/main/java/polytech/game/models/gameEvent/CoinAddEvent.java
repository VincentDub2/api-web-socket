package polytech.game.models.gameEvent;

public class CoinAddEvent extends GameEventMessage {
    public CoinAddEvent(CoinAddData data) {
        super("coinAdd", data);
    }
}
