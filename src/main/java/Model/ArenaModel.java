package Model;

import Observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class ArenaModel extends Observable<ArenaModel> {
    private final int width;
    private final int height;
    private List<Player> players; // Indice is the Player Number
    private List<Wall> walls;
    private Timer timer;

    public enum COLOR {
        GREEN("#00FF00", "GREEN"), BLUE("#4444FF", "BLUE"), RED("#FF0000", "RED"),
        DARKGREY("#111111", "DARKGREY"), WHITE("#FFFFFF", "WHITE"), PINK("#FF1493", "PINK"), YELLOW("#FFFF00", "YELLOW"),
        ORANGE("#FFA500", "ORANGE"), PURPLE("#8B008B", "PURPLE");

        private String str;
        private String name;

        COLOR(String s, String name) {
            this.str = s;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getStr() { return str; }
    }

    public ArenaModel(int width, int height) {
        this.width = width;
        this.height = height;
        this.players = new ArrayList<>();
        this.createPlayers(this.players);
        this.walls = new ArrayList<>();
        this.addLimitWalls(this.walls);
        this.timer = new Timer();
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Timer getTimer() {
        return timer;
    }

    public void createPlayers(List<Player> p) {
        Car p1 = new Car(new Position(1, this.height/2), COLOR.GREEN, Car.ORIENTATION.RIGHT, 'O');
        Car p2 = new Car(new Position(this.width - 2, this.height/2), COLOR.RED, Car.ORIENTATION.LEFT, '|');
        p.add(new Player(p1));
        p.add(new Player(p2));
    }

    public void addLimitWalls(List<Wall> w) {
        for (int i = 0; i < width; i++) {
            w.add(new Wall(new Position(i, 0), COLOR.DARKGREY));
            w.add(new Wall(new Position(i, height - 1), COLOR.DARKGREY));
        }
        for (int i = 0; i < height; i++) {
            w.add(new Wall(new Position(0, i), COLOR.DARKGREY));
            w.add(new Wall(new Position(width - 1, i), COLOR.DARKGREY));
        }
    }

    public void addWall(Wall w) {
        this.walls.add(w);
    }

    public void setCar(Integer playerNr, Car car) {
        this.players.get(playerNr - 1).setCar(car);
    }

    public Car getCar(Integer playerNr) {
        return this.players.get(playerNr - 1).getCar();
    }

    public boolean gameEnded() {
        int aliveCounter = 0;
        for (Player player : this.players) {
            if (player.getCar().isAlive()) aliveCounter++;
        }
        return aliveCounter < 2;
    }

    public int getWinner() {
        int i = 1;
        for (Player player : this.players) {
            if (player.car.isAlive()) return i;
            i++;
        }
        return -1;
    }

    public void addPlayer(Car c) {
        this.players.add(new Player(c));
    }
}
