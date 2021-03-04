package Controller;

import Controller.State.GameOverState;
import Controller.State.PauseState;
import Model.ArenaModel;
import Model.Car;
import View.ArenaView;
import View.State.GameOverView;
import View.State.PauseView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ArenaController {
    private Game game;
    private final ArenaView gui;
    private final ArenaModel arena;
    private final PlayerPositionUpdater playerUpdater;

    public enum COLLISION {
        NO, WALL, CAR;

        @Override
        public String toString() {
            if (this.equals(COLLISION.NO)) return "NO";
            if (this.equals(COLLISION.WALL)) return "WALL";
            if (this.equals(COLLISION.CAR)) return "CAR";
            return "";
        }
    }

    public ArenaController(Game game, PlayerPositionUpdater playerUpdater) {
        this.gui = game.getGui();
        this.arena = game.getArena();
        this.gui.changed(arena);
        this.playerUpdater = playerUpdater;
        this.game = game;
    }

    public void startThread() {
        this.playerUpdater.start();
    }

    public void stopThread() {
        this.playerUpdater.interrupt();
    }

    public boolean start() throws IOException, InterruptedException {
            this.arena.getTimer().update();
            ArenaView.COMMAND command = gui.getCommand();
            switch (command) {
                case UP1:
                    MutexSyncronize.lock();
                    arena.getCar(1).setOrientation(Car.ORIENTATION.UP);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case RIGHT1:
                    MutexSyncronize.lock();
                    arena.getCar(1).setOrientation(Car.ORIENTATION.RIGHT);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case DOWN1:
                    MutexSyncronize.lock();
                    arena.getCar(1).setOrientation(Car.ORIENTATION.DOWN);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case LEFT1:
                    MutexSyncronize.lock();
                    arena.getCar(1).setOrientation(Car.ORIENTATION.LEFT);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case UP2:
                    MutexSyncronize.lock();
                    arena.getCar(2).setOrientation(Car.ORIENTATION.UP);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case RIGHT2:
                    MutexSyncronize.lock();
                    arena.getCar(2).setOrientation(Car.ORIENTATION.RIGHT);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case DOWN2:
                    MutexSyncronize.lock();
                    arena.getCar(2).setOrientation(Car.ORIENTATION.DOWN);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case LEFT2:
                    MutexSyncronize.lock();
                    arena.getCar(2).setOrientation(Car.ORIENTATION.LEFT);
                    MutexSyncronize.unlock();
                    arena.notifyObservers(arena);
                    break;
                case ESC:
                    this.arena.getTimer().stop();
                    this.stopThread();
                    game.setGameState(new PauseState(game, new PauseView()));
                    return true;
                case NULL:
                    break;
            }
            if (arena.gameEnded()) {
                this.stopThread();
                TimeUnit.SECONDS.sleep(2);
                this.arena.getTimer().reset();
                game.setGameState(new GameOverState(game, new GameOverView(), new ScoresController()));
                return true;
            }
        return false;
    }
}
