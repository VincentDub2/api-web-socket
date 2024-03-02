package org.acme.game.websocket;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/game")
@ApplicationScoped
public class WebSocketServer {

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
        // Envoyer l'état initial du jeu au client connecté
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
        // Gérer la déconnexion du client
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Gérer les erreurs
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Traiter les messages reçus des clients
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
