package polytech.game.game.models;

public class GameEvent {
    private String message;

    public GameEvent(String message) {
        this.message = message;
    }

    // Getters
    public String getMessage() {
        return message;
    }
}