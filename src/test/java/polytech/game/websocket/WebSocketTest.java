package polytech.game.websocket;

import jakarta.websocket.*;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebSocketTest {

    private final CountDownLatch messageLatch = new CountDownLatch(1);

    @Test
    public void testWebSocket() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/game"; // Remplacez par l'URL de votre WebSocket
        Session session = container.connectToServer(new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig config) {
                session.addMessageHandler(new MessageHandler.Whole<String>() {
                    @Override
                    public void onMessage(String message) {
                        System.out.println("Message reçu : " + message);
                        messageLatch.countDown();
                    }
                });
                try {
                    session.getBasicRemote().sendText("test message");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, URI.create(uri));

        assertTrue(messageLatch.await(3, TimeUnit.SECONDS), "Le message attendu n'a pas été reçu");
        session.close();
    }
}
