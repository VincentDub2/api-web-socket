package polytech.game.game;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import polytech.game.game.models.*;
import polytech.game.models.gameEvent.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameService {

    @Inject
    Event<GameEventMessage> gameEvent;

    public void triggerEvent(GameEventMessage event) {
        gameEvent.fire(event);
    }
    private final Map gameMap;
    private final List<Car> cars = new ArrayList<>();
    
    // Setter pour l'injection/mock de gameEvent dans les tests
    public void setGameEvent(Event<GameEventMessage> gameEvent) {
        this.gameEvent = gameEvent;
    }

    public GameService() {
        // Ici, vous pouvez initialiser la carte du jeu, les voitures, etc.
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(100, 100, 200, 20));
        walls.add(new Wall(400, 100, 20, 200));
        walls.add(new Wall(100, 300, 200, 20));
        walls.add(new Wall(100, 300, 20, 200));
        walls.add(new Wall(100, 500, 200, 20));
        walls.add(new Wall(400, 500, 20, 200));
        walls.add(new Wall(400, 700, 200, 20));
        walls.add(new Wall(600, 500, 20, 200));
        walls.add(new Wall(600, 300, 200, 20));
        walls.add(new Wall(800, 300, 20, 200));
        walls.add(new Wall(800, 100, 200, 20));
        walls.add(new Wall(1000, 100, 20, 200));

        gameMap = new Map(walls, new ArrayList<>());

        // Ajout de pièces
        for (int i = 0; i < 10; i++) {
            gameMap.coins().add(new Coin((int) (Math.random() * 800), (int) (Math.random() * 600)));
        }

    }

    public Car addNewCar(String id) {
        Car car = new Car(id,(int) (Math.random() * 800),(int) (Math.random() * 600));
        cars.add(car);
        return car;
    }

    public void removeCar(String id) {
        cars.removeIf(car -> car.getId().equals(id));
    }

    public synchronized void moveCar(String carId, int direction) {
        Car car = findCarById(carId);
        if (car != null) {
            int newX = car.getX();
            int newY = car.getY();

            // Calcule la nouvelle position en fonction de la direction et de la vitesse
            switch (direction) {
                case 0: newY -= car.getSpeed(); break;
                case 1: newX += car.getSpeed(); break;
                case 2: newY += car.getSpeed(); break;
                case 3: newX -= car.getSpeed(); break;
            }

            Rectangle futureHitbox = new Rectangle(newX, newY,car.getWidth(),car.getHeight());

            for (Wall wall : gameMap.getWalls()) {
                Rectangle wallHitbox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
                if (intersects(futureHitbox, wallHitbox)) {
                    System.out.println("Car hit a wall");
                return;
                }
            }

            for (Coin coin : gameMap.getCoins()) {
                if (coin.isCollected()) {
                    continue;
                }
                Rectangle coinHitbox = new Rectangle(coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight());
                if (intersects(futureHitbox, coinHitbox)) {
                    System.out.println("Car collected a coin");
                    CoinCollectionData coinCollectionData = new CoinCollectionData(coin.getId(),true);
                    CoinCollectionEvent coinCollectionEvent = new CoinCollectionEvent(coinCollectionData);
                    triggerEvent(coinCollectionEvent);
                    coin.setCollected(true);
                    car.setScore(car.getScore() + 1);

                }
            }

            car.setX(newX);
            car.setY(newY);

            MovementData movementData = new MovementData(carId, newX, newY, car.getScore());
            MovementEvent movementEvent = new MovementEvent(movementData);
            triggerEvent(movementEvent);

        }
    }

    public boolean intersects(Rectangle r1, Rectangle r2) {
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x &&
                r1.y < r2.y + r2.height && r1.y + r1.height > r2.y;
    }


    public String getInitialState(String sessionId) {

        System.out.println("Getting initial state for session: " + sessionId);

        InitialGameState initialState = new InitialGameState();

        // Ici, vous remplissez l'état avec les données actuelles
        initialState.setCars(cars);

        initialState.setWalls(gameMap.walls());

        initialState.setCoins(gameMap.coins());

        initialState.setPlayerCar(findCarById(sessionId));

        // Sérialisation en JSON
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(initialState);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}"; // En cas d'erreur, retournez un objet vide (ou gérer l'erreur d'une autre manière)
        }
    }

    public Car findCarById(String id) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
    }

}
