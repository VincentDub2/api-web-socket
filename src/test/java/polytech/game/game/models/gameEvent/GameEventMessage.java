package polytech.game.game.models.gameEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameEventMessage {
    private String eventType;
    private Object eventData;

    public GameEventMessage(String eventType, Object eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Retourne un objet JSON vide en cas d'erreur
        }
    }

    // Getters
    public String getEventType() {
        return eventType;
    }

    public Object getEventData() {
        return eventData;
    }
}

