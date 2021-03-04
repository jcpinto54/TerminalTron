package Model;

import java.util.ArrayList;
import java.util.List;

public class Car implements Cloneable {
    private char WallChar;
    private Position position;
    private double speed;
    private int lives;
    private ArenaModel.COLOR color;
    private ORIENTATION orientation;
    private List<Wall> walls;
    private int recovering = 0;

    public Car(Position position, ArenaModel.COLOR color, ORIENTATION orientation, char WallChar) {
        this.lives = 3;
        this.position = position;
        this.speed = 1;
        this.color = color;
        this.orientation = orientation;
        this.walls = new ArrayList<>();
        this.WallChar = WallChar;
    }

    public enum ORIENTATION {
        UP('^'), RIGHT('>'), LEFT('<'), DOWN('v');

        private char representation;

        ORIENTATION(char c) {
            this.representation = c;
        }

        public char getRepresentation() {
            return representation;
        }

        public char getOpposite() {
            switch (representation) {
                case '^': return 'v';
                case '>': return '<';
                case 'v': return '^';
                case '<': return '>';
            }
            return '-';
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getLives() {
        return lives;
    }

    public ORIENTATION getOrientation() {
        return this.orientation;
    }

    public void setOrientation(ORIENTATION orientation) {
        if (this.orientation.getOpposite() == orientation.getRepresentation() && this.getRecovering() == 0) return;
        this.orientation = orientation;
    }

    public ArenaModel.COLOR getColor() {
        return color;
    }

    public void setColor(ArenaModel.COLOR color) {
        this.color = color;
    }

    public boolean isAlive() {return lives > 0;}

    public char getWallChar() {
        return WallChar;
    }

    public void setWallChar(char WallChar) {
        this.WallChar = WallChar;
    }

    public int getRecovering() {
        return recovering;
    }

    public void recover() {
        recovering--;
    }

    public void loseLife() {
        if (!this.isAlive()) return;
        this.lives--;
        this.recovering = 30;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void addWall(Wall wall) {
        this.walls.add(wall);
    }

    @Override
    public String toString() {
        return color.getStr();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Car)) {
            return false;
        }

        Car car = (Car) o;

        boolean wallsEqual = true;
        if (this.walls.size() != car.walls.size()) wallsEqual = false;
        else {
            for (int i = 0; i < this.walls.size(); i++) {
                if (this.walls.get(i) != car.walls.get(i)) {
                    wallsEqual = false;
                    break;
                }
            }
        }

        return this.color.getStr().equals(car.getColor().getStr())
                && wallsEqual && this.orientation.representation == car.orientation.representation;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Car c = (Car) super.clone();
        c.position = new Position(this.position.getX(), this.position.getY());
        c.speed = this.speed;
        c.lives = this.lives;
        c.color = this.color;
        c.orientation = this.orientation;
        c.walls = new ArrayList<>();
        c.walls.addAll(walls);
        c.recovering = this.recovering;

        return c;
    }

}
