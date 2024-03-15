package polytech.game.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InitialGameStateTest {

    private InitialGameState initialGameState;
    private Car car;
    private Coin coin;
    private Wall wall;

    @BeforeEach
    public void setup() {
        List<Car> cars = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();

        car = new Car("car1", 100, 200);
        coin = new Coin(10, 20);
        wall = new Wall(300, 400, 50, 60); // Hypothetical Wall constructor

        cars.add(car);
        coins.add(coin);
        walls.add(wall);

        initialGameState = new InitialGameState(cars, walls, coins, car);
    }

    @Test
    public void testInitialStateSetup() {
        assertEquals(1, initialGameState.getCars().size());
        assertEquals(1, initialGameState.getWalls().size());
        assertEquals(1, initialGameState.getCoins().size());
        assertNotNull(initialGameState.getPlayerCar());
    }

    @Test
    public void testAddAndRemoveElements() {
        // Test adding elements
        Car newCar = new Car("car2", 200, 300);
        Coin newCoin = new Coin(30, 40);
        Wall newWall = new Wall(500, 600, 70, 80); // Hypothetical Wall constructor

        initialGameState.addCar(newCar);
        initialGameState.addCoin(newCoin);
        initialGameState.addWall(newWall);

        assertEquals(2, initialGameState.getCars().size());
        assertEquals(2, initialGameState.getCoins().size());
        assertEquals(2, initialGameState.getWalls().size());

        // Test removing elements by object
        initialGameState.removeCar(car);
        initialGameState.removeCoin(coin);
        initialGameState.removeWall(wall);

        assertEquals(1, initialGameState.getCars().size());
        assertEquals(1, initialGameState.getCoins().size());
        assertEquals(1, initialGameState.getWalls().size());

        // Test removing elements by index
        initialGameState.removeCar(0);
        initialGameState.removeCoin(0);
        initialGameState.removeWall(0);

        assertTrue(initialGameState.getCars().isEmpty());
        assertTrue(initialGameState.getCoins().isEmpty());
        assertTrue(initialGameState.getWalls().isEmpty());
    }
}
