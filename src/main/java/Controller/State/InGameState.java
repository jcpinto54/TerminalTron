package Controller.State;

import Controller.ArenaController;
import Controller.Game;
import Controller.PlayerPositionUpdater;
import Model.ArenaModel;

import java.io.IOException;

public class InGameState extends GameState {
    ArenaController controller;

    public InGameState(Game game) {
        super(game);
        this.controller = new ArenaController(game, new PlayerPositionUpdater(game.getArena()));
    }

    @Override
    public boolean run() {
        game.getScreen().clear();
        this.controller.startThread();
        this.game.getArena().getTimer().start();
        while (true) {
            try {
                if (this.controller.start()) {
                    break;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}