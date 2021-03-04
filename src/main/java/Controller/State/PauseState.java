package Controller.State;

import Controller.ArenaController;
import Controller.Game;
import Controller.PlayerPositionUpdater;
import View.State.MainMenuView;
import View.State.MenuView;
import View.State.PauseView;

import java.io.IOException;

public class PauseState extends GameState {
    PauseView view;

    public PauseState(Game game, PauseView view) {
        super(game);
        this.view = view;
    }

    @Override
    public void draw() throws IOException {
        view.draw(game);
    }

    @Override
    public boolean processKey() throws IOException {
        MenuView.SELECTED command = view.getKey(game);
        switch (command) {
            case ENTER:
                this.game.getArena().getTimer().start();
                game.setGameState(new InGameState(game));
                break;
            case EXIT:
                game.resetGame();
                game.setGameState(new MenuState(game, new MainMenuView()));
        }
        return false;
    }

    @Override
    public boolean run() {
        try {
            draw();
            processKey();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
