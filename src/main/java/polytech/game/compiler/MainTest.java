package polytech.game.compiler;


import polytech.game.models.Car;
//import polytech.game.compiler.CarController;
public class MainTest {
    public static void main(String[] args) {
        Car car = new Car("1", 0, 0);
        //CarController controller = new CarController();

        // Exemple d'appel de méthode
       // controller.moveCar0(car);
        System.out.println("Position de la voiture : " + car.getX() + ", " + car.getY());

        //controller.moveCar1(car);
        System.out.println("Position de la voiture : " + car.getX() + ", " + car.getY());
    }
}