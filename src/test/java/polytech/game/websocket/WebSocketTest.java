package polytech.game.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import org.junit.jupiter.api.Test;
import polytech.game.GameService;
import polytech.game.models.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class WebSocketServerTest {

    @Test
    void onMessageShouldMoveCar() {
        // Setup
        GameService mockGameService = mock(GameService.class);
        WebSocketServer server = new WebSocketServer();
        server.gameService = mockGameService; // Supposons une manière d'injecter cela
        String sessionId = "session1";
        Session mockSession = mock(Session.class);
        when(mockSession.getId()).thenReturn(sessionId);
        server.sessions.put(sessionId, mockSession);

        // Action
        server.onMessage("carId:1", mockSession);

        // Assert
        verify(mockGameService).moveCar("carId", 1);
    }

    @Test
    void onOpenShouldAddSessionAndSendInitialState() {
        GameService mockGameService = mock(GameService.class);
        WebSocketServer server = new WebSocketServer();
        server.gameService = mockGameService;

        Session mockSession = mock(Session.class);
        when(mockSession.getId()).thenReturn("session1");

        Car mockCar = new Car("carId", 3, 4);
        when(mockGameService.addNewCar(anyString())).thenReturn(mockCar);

        when(mockGameService.getInitialState(anyString())).thenReturn("initialState");

        // Pour éviter NullPointerException sur getAsyncRemote()
        RemoteEndpoint.Async asyncMock = mock(RemoteEndpoint.Async.class);
        when(mockSession.getAsyncRemote()).thenReturn(asyncMock);

        server.onOpen(mockSession);

        assertTrue(server.sessions.containsKey("session1"));
        verify(mockSession.getAsyncRemote()).sendText("initialState");
    }
    @Test
    void onCloseShouldRemoveSessionAndCar() {
        GameService mockGameService = mock(GameService.class);
        WebSocketServer server = new WebSocketServer();
        server.gameService = mockGameService;
        Session mockSession = mock(Session.class);
        when(mockSession.getId()).thenReturn("session1");
        server.sessions.put("session1", mockSession);

        server.onClose(mockSession);

        assertFalse(server.sessions.containsKey("session1"));
        verify(mockGameService).removeCar("session1");
    }

    @Test
    void broadcastShouldSendMessageToAllSessions() throws JsonProcessingException {
        GameService mockGameService = mock(GameService.class);
        ObjectMapper mockMapper = mock(ObjectMapper.class);
        WebSocketServer server = new WebSocketServer();
        server.gameService = mockGameService;
        server.mapper = mockMapper;
        Session mockSession1 = mock(Session.class);
        Session mockSession2 = mock(Session.class);

        // Ajouter ceci pour chaque session mockée
        RemoteEndpoint.Async asyncMock1 = mock(RemoteEndpoint.Async.class);
        when(mockSession1.getAsyncRemote()).thenReturn(asyncMock1);

        RemoteEndpoint.Async asyncMock2 = mock(RemoteEndpoint.Async.class);
        when(mockSession2.getAsyncRemote()).thenReturn(asyncMock2);

        server.sessions.put("session1", mockSession1);
        server.sessions.put("session2", mockSession2);
        GameStateCarChange message = new GameStateCarChange(); // Supposons que c'est un objet valide
        String messageJson = "{\"test\":\"message\"}";
        when(mockMapper.writeValueAsString(message)).thenReturn(messageJson);

        server.broadcast(message);

        // Mise à jour des vérifications pour utiliser argThat
        // Exemple de vérification en ignorant le deuxième argument, si applicable
        verify(asyncMock1).sendText(anyString(), any(SendHandler.class));
        verify(asyncMock2).sendText(anyString(), any(SendHandler.class));

    }
}
