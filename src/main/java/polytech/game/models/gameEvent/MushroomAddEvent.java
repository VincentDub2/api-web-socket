package polytech.game.models.gameEvent;

public class MushroomAddEvent extends GameEventMessage{
    public MushroomAddEvent(MushroomAddData data) {
        super("MushroomAddEvent", data);
    }
}
