package polytech.game.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("testCar", 100, 200);
    }

    @Test
    void testCarInitialization() {
        assertAll("Car should be initialized with correct values",
                () -> assertEquals("testCar", car.getId()),
                () -> assertEquals(100, car.getX()),
                () -> assertEquals(200, car.getY()),
                () -> assertEquals(8, car.getSpeed()),
                () -> assertEquals(0, car.getDirection()),
                () -> assertEquals(0, car.getScore())
        );
    }

    @Test
    void testSettersAndGetters() {
        car.setX(150);
        car.setY(250);
        car.setSpeed(25);
        car.setDirection(1);
        car.setScore(10);
        car.setWidth(35);
        car.setHeight(30);

        assertAll("Setters and Getters should work correctly",
                () -> assertEquals(150, car.getX()),
                () -> assertEquals(250, car.getY()),
                () -> assertEquals(25, car.getSpeed()),
                () -> assertEquals(1, car.getDirection()),
                () -> assertEquals(10, car.getScore()),
                () -> assertEquals(35, car.getWidth()),
                () -> assertEquals(30, car.getHeight())
        );
    }

    @Test
    void testGetHitbox() {
        Rectangle expectedHitbox = new Rectangle(100, 200, 30, 36);
        assertEquals(expectedHitbox, car.getHitbox(), "Hitbox should match the car's position and size");
    }
}
