package Controller.State;

import Controller.Game;
import View.State.HighScoresView;
import View.State.MainMenuView;
import View.State.MenuView;
import View.State.OptionsView;

import java.io.IOException;

public class MenuState extends GameState {
    MainMenuView view;

    public MenuState(Game game, MainMenuView view) {
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
                game.setGameState(new InGameState(game));
                return false;
            case OPTIONS:
                game.setGameState(new OptionsState(game, new OptionsView()));
                return false;
            case HIGHSCORES:
                game.setGameState(new HighScoresState(game, new HighScoresView()));
                return false;
            case EXIT:
                return true;
        }
        return command != MenuView.SELECTED.NULL;
    }

    @Override
    public boolean run() {
        try {
            draw();
            if (processKey()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
