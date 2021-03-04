package Model;

public class Player {
    Car car;
    int points = 0;

    public int getPoints() {
        return points;
    }

    public void incrementPoints() {
        this.points++;
    }

    public Player(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
