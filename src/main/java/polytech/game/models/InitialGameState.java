package polytech.game.models;
import java.util.List;
public class InitialGameState {
    private List<Car> cars;
    private List<Wall> walls;
    private List<Coin> coins;

    private List<Mushroom> mushrooms;

    private Car playerCar;
    public InitialGameState() {
    }

    public InitialGameState(List<Car> cars, List<Wall> walls, List<Coin> coins, Car playerCar, List<Mushroom> mushrooms) {
        this.cars = cars;
        this.walls = walls;
        this.coins = coins;
        this.playerCar = playerCar;
        this.mushrooms = mushrooms;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(List<Mushroom> mushrooms) {
        this.mushrooms = mushrooms;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public Car getPlayerCar() {
        return playerCar;
    }

    public void setPlayerCar(Car playerCar) {
        this.playerCar = playerCar;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public void addCoin(Coin coin) {
        this.coins.add(coin);
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    public void addWall(Wall wall) {
        this.walls.add(wall);
    }

    public void removeCoin(Coin coin) {
        this.coins.remove(coin);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
    }

    public void removeWall(Wall wall) {
        this.walls.remove(wall);
    }

    public void removeCoin(int index) {
        this.coins.remove(index);
    }

    public void removeCar(int index) {
        this.cars.remove(index);
    }

    public void removeWall(int index) {
        this.walls.remove(index);
    }

}
