package polytech.game.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import polytech.game.GameService;
import polytech.game.models.Car;
import polytech.game.models.GameStateCarChange;
import polytech.game.models.gameEvent.GameEventMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/game")
@ApplicationScoped
public class WebSocketServer {

    Map<String, Session> sessions = new ConcurrentHashMap<>();
    @Inject
    GameService gameService;

    public void onGameEvent(@Observes GameEventMessage event) {
        broadcast(event);
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
        // Créer et assigner une nouvelle voiture au joueur
        Car newCar = gameService.addNewCar(session.getId());
        System.out.println("New car created with id: " + newCar.getId());
        // Envoyer l'état initial du jeu au client connecté
        String initialState = gameService.getInitialState(session.getId());

        System.out.println("Sending initial state to client: " + initialState);
        session.getAsyncRemote().sendText(initialState);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
        // Gérer la déconnexion du client
        gameService.removeCar(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Gérer les erreurs
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Supposons que le message contient l'ID de la voiture et la direction sous la forme "carId:direction"
        String[] parts = message.split(":");
        if (parts.length == 2) {
            String carId = parts[0];
            int direction;
            try {
                direction = Integer.parseInt(parts[1]);
                gameService.moveCar(carId, direction);

            } catch (NumberFormatException e) {
                session.getAsyncRemote().sendText("Message invalide. Format attendu: 'carId:direction'.");
            }
        }
    }

    public void broadcast(Object message) {

        String jsonMessage;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonMessage = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            System.err.println("Erreur lors de la conversion du message en JSON : " + e.getMessage());
            jsonMessage = "{\"error\":\"Problème interne du serveur\"}";
        }

        final String finalJsonMessage = jsonMessage;

        // Envoi du message JSON à tous les clients connectés
        sessions.values().forEach(session -> {
            session.getAsyncRemote().sendText(finalJsonMessage, result -> {
                if (result.getException() != null) {
                    System.err.println("Erreur lors de l'envoi du message : " + result.getException().getMessage());
                }
            });
        });
    }


    private String convertChangeToJson(GameStateCarChange change) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(change);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public void sendToClient(String sessionId, Object message) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            try {
                String jsonMessage = new ObjectMapper().writeValueAsString(message);
                session.getAsyncRemote().sendText(jsonMessage);
            } catch (JsonProcessingException e) {
                System.err.println("Erreur lors de la conversion du message en JSON : " + e.getMessage());
                // Gérer l'erreur, par exemple, en envoyant un message d'erreur générique au client
                session.getAsyncRemote().sendText("{\"error\":\"Problème interne du serveur\"}");
            }
        } else {
            System.err.println("Tentative d'envoi de message à une session inexistante : " + sessionId);
        }
    }

}
