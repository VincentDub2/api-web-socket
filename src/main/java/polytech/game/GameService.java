package polytech.game;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import polytech.game.models.*;
import polytech.game.models.gameEvent.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        gameMap = new Map(walls, new ArrayList<>(), new ArrayList<>());

        scheduleMushroomSpawns();
        sheduleCoinSpawns();


    }

    public boolean isInWall(int x, int y) {
        for (Wall wall : gameMap.getWalls()) {
            Rectangle wallHitbox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (wallHitbox.contains(x, y)) {
                return true;
            }
        }
        return false;
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
            final int steps = car.getSpeed() / 2; // Nombre d'étapes basé sur la vitesse, ajustez selon vos besoins
            final int singleStepDistance = 4; // Distance parcourue à chaque étape, ajustez selon vos besoins

            for (int i = 0; i < steps; i++) {
                // Calculez la nouvelle position pour chaque étape
                int deltaX = 0;
                int deltaY = 0;
                switch (direction) {
                    case 0: deltaY -= singleStepDistance; break;
                    case 1: deltaX += singleStepDistance; break;
                    case 2: deltaY += singleStepDistance; break;
                    case 3: deltaX -= singleStepDistance; break;
                }
                updatePosition(car, deltaX, deltaY); // Mettre à jour la position et vérifier les collisions
                sendPositionUpdate(car); // Envoyer la mise à jour au front-end

                try {
                    Thread.sleep(10); // Attendez un peu avant de faire le prochain pas pour simuler la vitesse, ajustez selon vos besoins
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private void updatePosition(Car car, int deltaX, int deltaY) {
        int newX = car.getX() + deltaX;
        int newY = car.getY() + deltaY;

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
                gameMap.setCoinCollected(coin.getX(), coin.getY());
                car.setScore(car.getScore() + 1);

            }
        }

        for (Mushroom mushroom : gameMap.getMushrooms()) {
            if (mushroom.isCollected()) {
                continue;
            }
            Rectangle mushroomHitbox = new Rectangle(mushroom.getX(), mushroom.getY(), mushroom.getWidth(), mushroom.getHeight());
            if (intersects(futureHitbox, mushroomHitbox)) {
                mushroom.setCollected(true);
                car.setSpeed(car.getSpeed() + 10); // Assume SPEED_BOOST est une constante définie

                MushroomCollectionData mushroomCollectionData = new MushroomCollectionData(mushroom.getId(),true);
                MushroomCollectionEvent mushroomCollectionEvent = new MushroomCollectionEvent(mushroomCollectionData);
                triggerEvent(mushroomCollectionEvent);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        car.setSpeed(car.getSpeed() - 10);
                    }
                }, 4000); // Réinitialiser la vitesse après 4 secondes
            }
        }

        car.setX(newX);
        car.setY(newY);
    }

    private void sendPositionUpdate(Car car) {
        MovementData movementData = new MovementData(car.getId(), car.getX(), car.getY(), car.getScore());
        MovementEvent movementEvent = new MovementEvent(movementData);
        triggerEvent(movementEvent);
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

    private void scheduleMushroomSpawns() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameMap.getMushrooms().size() < 3) {
                    generateMushroom();
                }
            }
        }, 0, 10000); // Exemple : toutes les 10 secondes
    }

    private void generateMushroom() {
        // Utilisation d'un thread séparé pour la génération des champignons
        new Thread(() -> {
            Mushroom mushroom = new Mushroom((int) (Math.random() * 800), (int) (Math.random() * 600));
            do {
                mushroom.setX((int) (Math.random() * 800));
                mushroom.setY((int) (Math.random() * 600));
            } while (isInWall(mushroom.getX(), mushroom.getY()));

            // Assurez-vous que l'ajout du champignon à la liste est thread-safe
            synchronized (gameMap.getMushrooms()) {
                gameMap.getMushrooms().add(mushroom);
            }

            MushroomAddData mushroomAddData = new MushroomAddData(mushroom.getId(), mushroom.getX(), mushroom.getY());
            MushroomAddEvent mushroomAddEvent = new MushroomAddEvent(mushroomAddData);
            triggerEvent(mushroomAddEvent);
        }).start();
    }

    private void sheduleCoinSpawns() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameMap.getCoins().size() < 5) {
                    generateCoin();
                }
            }
        }, 0, 5000); // Exemple : toutes les 5 secondes
    }

    private void generateCoin() {
        // Utilisation d'un thread séparé pour la génération des pièces
        new Thread(() -> {
            Coin coin = new Coin((int) (Math.random() * 800), (int) (Math.random() * 600));
            do {
                coin.setX((int) (Math.random() * 800));
                coin.setY((int) (Math.random() * 600));
            } while (isInWall(coin.getX(), coin.getY()));

            // Assurez-vous que l'ajout de la pièce à la liste est thread-safe
            synchronized (gameMap.getCoins()) {
                gameMap.getCoins().add(coin);
            }

            CoinAddData coinAddData = new CoinAddData(coin.getId(), coin.getX(), coin.getY());
            CoinAddEvent coinAddEvent = new CoinAddEvent(coinAddData);
            triggerEvent(coinAddEvent);
        }).start();
    }

}



