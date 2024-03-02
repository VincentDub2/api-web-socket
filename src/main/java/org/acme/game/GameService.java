package org.acme.game;


import jakarta.enterprise.context.ApplicationScoped;
import org.acme.game.models.Car;
import org.acme.game.models.Coin;
import org.acme.game.models.Map;
import org.acme.game.models.Wall;


import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameService {
    private Map gameMap;
    private List<Car> cars = new ArrayList<>();

    public GameService() {
        // Initialiser la carte, les murs, et les pièces ici (voir les classes Wall, Coin et Map)

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

    private Car findCarById(String id) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
    }

    // Autres méthodes utiles (ajout de voiture, gestion des collisions, etc.)
}
