package polytech.game.models;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WallTest {

    @Test
    public void testWallInitialization() {
        Wall wall = new Wall(100, 200, 50, 60);
        assertEquals(100, wall.getX());
        assertEquals(200, wall.getY());
        assertEquals(50, wall.getWidth());
        assertEquals(60, wall.getHeight());
    }

    @Test
    public void testGetHitbox() {
        Wall wall = new Wall(100, 200, 50, 60);
        Rectangle expectedHitbox = new Rectangle(100, 200, 50, 60);
        Rectangle actualHitbox = wall.getHitbox();

        assertEquals(expectedHitbox, actualHitbox);
    }

    @Test
    public void testSetters() {
        Wall wall = new Wall(100, 200, 50, 60);
        wall.setX(150);
        wall.setY(250);

        assertEquals(150, wall.getX());
        assertEquals(250, wall.getY());

        // Assurez-vous que la hitbox est mise à jour correctement après le changement de position
        Rectangle expectedUpdatedHitbox = new Rectangle(150, 250, 50, 60);
        assertEquals(expectedUpdatedHitbox, wall.getHitbox());
    }
}
