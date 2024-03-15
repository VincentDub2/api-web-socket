package polytech.game.models;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
public class CoinTest {

    @Test
    public void testCoinInitializationAndCollection() {
        Coin coin1 = new Coin(100, 200);
        Coin coin2 = new Coin(150, 250);
        assertNotEquals(coin1.getId(), coin2.getId()); // S'assurer que les IDs sont uniques et auto-incrémentés
        assertFalse(coin1.isCollected());

        coin1.setCollected(true);
        assertTrue(coin1.isCollected());
    }

    @Test
    public void testCoinHitbox() {
        Coin coin = new Coin(100, 200);
        Rectangle hitbox = coin.getHitbox();
        assertNotNull(hitbox);
        assertEquals(new Rectangle(100, 200, 2, 2), hitbox);
    }
}
