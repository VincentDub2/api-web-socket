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
            // Calculer la nouvelle position en fonction de la direction
            // Vérifier les collisions avec les murs ou les pièces
            // Mettre à jour la position de la voiture si aucun mur n'est touché
            // Collecter les pièces si possible et mettre à jour le score
        }
    }

    private Car findCarById(String id) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
    }

    // Autres méthodes utiles (ajout de voiture, gestion des collisions, etc.)
}
