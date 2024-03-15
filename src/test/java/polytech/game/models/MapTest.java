package polytech.game.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    private Map map;
    private List<Wall> walls;
    private List<Coin> coins;

    @BeforeEach
    public void setup() {
        walls = new ArrayList<>();
        coins = new ArrayList<>();
        walls.add(new Wall(100, 100, 10, 10)); // Hypothetical Wall constructor
        coins.add(new Coin(200, 200));
        map = new Map(walls, coins);
    }

    @Test
    public void testMapInitialization() {
        assertEquals(walls, map.getWalls());
        assertEquals(coins, map.getCoins());
    }

    @Test
    public void testIsWall() {
        assertTrue(map.isWall(100, 100));
        assertFalse(map.isWall(150, 150));
    }

    @Test
    public void testIsCoin() {
        assertTrue(map.isCoin(200, 200));
        assertFalse(map.isCoin(250, 250));
    }

    @Test
    public void testSetCoinCollected() {
        assertFalse(coins.get(0).isCollected());
        map.setCoinCollected(200, 200, true);
        assertTrue(coins.get(0).isCollected());
    }
}
