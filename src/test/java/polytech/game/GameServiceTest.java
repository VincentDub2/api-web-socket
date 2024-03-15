package polytech.game;

import jakarta.enterprise.event.Event;
import polytech.game.models.*;
import polytech.game.models.gameEvent.GameEventMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;
    private Event<GameEventMessage> gameEventMock;


    @BeforeEach
    void setUp() {
        gameService = new GameService();
        gameEventMock = Mockito.mock(Event.class);
        // Injecte le mock dans gameService
        gameService.setGameEvent(gameEventMock);
    }

    @Test
    void addAndRemoveCar() {
        String carId = "testCar";
        Car car = gameService.addNewCar(carId);
        assertNotNull(car);
        assertEquals(carId, car.getId());

        gameService.removeCar(carId);
        assertNull(gameService.findCarById(carId));
    }

    @Test
    public void testMoveCar() {
        Car car = gameService.addNewCar("movingCar");
        car.setX(0);
        car.setY(0);
        int initialX = car.getX();
        int initialY = car.getY();

        gameService.moveCar("movingCar", 1); // Assume 1 means moving east

        // Check if car has moved east
        assertTrue(car.getX() > initialX);
        assertEquals(initialY, car.getY());

        // Cleanup
        gameService.removeCar("movingCar");
    }

    @Test
    void initialStateSerialization() {
        String sessionId = "testSession";
        String initialStateJSON = gameService.getInitialState(sessionId);
        assertNotNull(initialStateJSON);
        assertFalse(initialStateJSON.isEmpty());

        // Vérifiez la présence de certaines clés dans le JSON pour s'assurer que la sérialisation fonctionne comme prévu
        assertTrue(initialStateJSON.contains("cars"));
        assertTrue(initialStateJSON.contains("walls"));
        assertTrue(initialStateJSON.contains("coins"));
    }
}
