package org.acme.game;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.game.models.*;


import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameService {
    private final Map gameMap;
    private final List<Car> cars = new ArrayList<>();

    public GameService() {
        // Ici, vous pouvez initialiser la carte du jeu, les voitures, etc.
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(100, 100));
        walls.add(new Wall(200, 100));
        walls.add(new Wall(300, 100));
        walls.add(new Wall(400, 100));
        walls.add(new Wall(500, 100));
        walls.add(new Wall(600, 100));
        walls.add(new Wall(700, 100));
        walls.add(new Wall(100, 200));
        walls.add(new Wall(100, 300));
        walls.add(new Wall(100, 400));
        walls.add(new Wall(100, 500));
        walls.add(new Wall(200, 500));
        walls.add(new Wall(300, 500));
        walls.add(new Wall(400, 500));
        walls.add(new Wall(500, 500));
        walls.add(new Wall(600, 500));
        walls.add(new Wall(700, 500));
        walls.add(new Wall(700, 200));
        walls.add(new Wall(700, 300));
        walls.add(new Wall(700, 400));
        gameMap = new Map(walls, new ArrayList<>());

        // Ajout de pièces
        for (int i = 0; i < 10; i++) {
            gameMap.coins().add(new Coin((int) (Math.random() * 800), (int) (Math.random() * 600)));
        }

    }

    public Car addNewCar(String id) {
        Car car = new Car(id,0,0);
        cars.add(car);
        return car;
    }

    public void removeCar(String id) {
        cars.removeIf(car -> car.getId().equals(id));
    }

    public synchronized void moveCar(String carId, int direction) {
        Car car = findCarById(carId);
        if (car != null) {
            int x = car.getX();
            int y = car.getY();

            // On calcule la nouvelle position du joueur
            switch (direction) {
                case 0:
                    y -= car.getSpeed();
                    break;
                case 1:
                    x += car.getSpeed();
                    break;
                case 2:
                    y += car.getSpeed();
                    break;
                case 3:
                    x -= car.getSpeed();
                    break;
            }

            // On vérifie si la nouvelle position est valide
            if (x >= 0 && x < 800 && y >= 0 && y < 600 && !gameMap.isWall(x, y)) {
                car.setX(x);
                car.setY(y);
                // On vérifie si le joueur a ramassé une pièce
                if (gameMap.isCoin(x, y)) {
                    car.setScore(car.getScore() + 1);
                    // On marque la pièce comme ramassée
                    gameMap.setCoinCollected(x, y, true);
                }
            }



        }
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

    private Car findCarById(String id) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
    }

}
