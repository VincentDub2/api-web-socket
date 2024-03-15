package polytech.game.game.models.gameEvent;

public class MovementEvent extends GameEventMessage {
    public MovementEvent(MovementData data) {
        super("movement", data);
    }
}