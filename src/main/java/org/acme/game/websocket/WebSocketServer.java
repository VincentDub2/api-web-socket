package org.acme.game.websocket;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.acme.game.GameService;
import org.acme.game.models.Car;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/game")
@ApplicationScoped
public class WebSocketServer {

    Map<String, Session> sessions = new ConcurrentHashMap<>();
    @Inject
    GameService gameService;

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

                // Après avoir déplacé la voiture, envoyez l'état mis à jour à tous les clients
                String gameState = "L'état mis à jour du jeu"; // Ici, vous devriez générer l'état actuel du jeu
                broadcast(gameState);
            } catch (NumberFormatException e) {
                session.getAsyncRemote().sendText("Message invalide. Format attendu: 'carId:direction'.");
            }
        }
    }

    // Méthode pour envoyer des messages à tous les clients connectés
    public void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    // Gérer les erreurs d'envoi de message
                }
            });
        });
    }
}
