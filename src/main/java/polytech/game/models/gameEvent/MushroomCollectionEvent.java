package polytech.game.models.gameEvent;

public class MushroomCollectionEvent extends  GameEventMessage{
    public MushroomCollectionEvent(MushroomCollectionData data) {
        super("mushroomCollection", data);
    }
}
