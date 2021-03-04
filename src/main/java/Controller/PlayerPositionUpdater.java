package Controller;

import Model.*;
import javafx.util.Pair;

import java.util.Random;

public class PlayerPositionUpdater extends Thread {
    private ArenaModel arena;
    private long initTime;

    private boolean testMode = false;

    public void testModeOn() {
        testMode = true;
    }

    public PlayerPositionUpdater(ArenaModel arena) {
        this.arena = arena;
    }

    public void randomCarPosition(Car car) {
        Random rand = new Random();
        Position pos;
        do {
            pos = new Position(rand.nextInt(arena.getWidth() - 2) + 1, rand.nextInt(arena.getHeight() - 2) + 1);
            car.setPosition(pos);
        } while (checkCollision(car, arena).getKey() != ArenaController.COLLISION.NO);
    }

    public Pair<ArenaController.COLLISION, Player> checkCollision(Car car, ArenaModel arena) {
        ArenaController.COLLISION collision = ArenaController.COLLISION.NO;
        Player colided = null;
        MutexSyncronize.lock();
        for (Wall wall : arena.getWalls()) {
            if (wall.getPosition().equals(car.getPosition())) {
                collision = ArenaController.COLLISION.WALL;
                MutexSyncronize.unlock();
                return new Pair<>(collision, colided);
            }
        }
        for (Player player : arena.getPlayers()) {
            if (!car.equals(player.getCar())) {
                if (car.getPosition().equals(player.getCar().getPosition())) {
                    collision = ArenaController.COLLISION.CAR;
                    colided = player;
                    MutexSyncronize.unlock();
                    return new Pair<>(collision, colided);
                }
            }
            for (Wall wall : player.getCar().getWalls()) {
                if (wall.getPosition().equals(car.getPosition())) {
                    collision = ArenaController.COLLISION.WALL;
                    MutexSyncronize.unlock();
                    return new Pair<>(collision, colided);
                }
            }
        }
        MutexSyncronize.unlock();
        return new Pair<>(collision, colided);

    }

    public void processCollision(Car car, Pair<ArenaController.COLLISION, Player> collisionCheck) {
        switch(collisionCheck.getKey()) {
            case NO: return;
            case WALL:
                car.getWalls().clear();
                randomCarPosition(car);
                car.loseLife();
                break;
            case CAR:
                car.getWalls().clear();
                randomCarPosition(car);
                car.loseLife();
                collisionCheck.getValue().getCar().getWalls().clear();
                randomCarPosition(collisionCheck.getValue().getCar());
                collisionCheck.getValue().getCar().loseLife();
                break;
        }
    }

    public void advance(Player player) {
        MutexSyncronize.lock();
        Car car = player.getCar();
        if (!car.isAlive()) {
            MutexSyncronize.unlock();
            return;
        }

        if (car.getRecovering() > 0) {
            car.recover();
            MutexSyncronize.unlock();
            return;
        }
        if (car.getOrientation().getRepresentation() == '^') {
            car.addWall(new Wall(car.getPosition(), car.getColor()));
            player.incrementPoints();
            car.setPosition(car.getPosition().up());
            MutexSyncronize.unlock();
            return;
        }
        if (car.getOrientation().getRepresentation() == '>') {
            car.addWall(new Wall(car.getPosition(), car.getColor()));
            player.incrementPoints();
            car.setPosition(car.getPosition().right());
            MutexSyncronize.unlock();
            return;
        }
        if (car.getOrientation().getRepresentation() == 'v') {
            car.addWall(new Wall(car.getPosition(), car.getColor()));
            player.incrementPoints();
            car.setPosition(car.getPosition().down());
            MutexSyncronize.unlock();
            return;
        }
        if (car.getOrientation().getRepresentation() == '<') {
            car.addWall(new Wall(car.getPosition(), car.getColor()));
            player.incrementPoints();
            car.setPosition(car.getPosition().left());
            MutexSyncronize.unlock();
            return;
        }
        MutexSyncronize.unlock();
    }

    @Override
    public void run() {
        initTime = System.currentTimeMillis();
        while(true) {
            if (this.isInterrupted()) return;
            long curTime = System.currentTimeMillis();
            if (curTime - initTime > 100) {
                for (Player player : arena.getPlayers()) {
                    advance(player);
                    Pair<ArenaController.COLLISION, Player> check = checkCollision(player.getCar(), arena);
                    processCollision(player.getCar(), check);
                    if (check.getKey() == ArenaController.COLLISION.CAR) {
                        break;
                    }
                }
                arena.notifyObservers(arena);
                if (testMode) return;
                initTime = curTime;
            }
        }
    }
}
