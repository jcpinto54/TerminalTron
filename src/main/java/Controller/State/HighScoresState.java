package Controller.State;

import Controller.Game;
import View.State.HighScoresView;
import View.State.MainMenuView;
import View.State.MenuView;

import java.io.IOException;


public class HighScoresState extends GameState {
    HighScoresView view;

    public HighScoresState(Game game, HighScoresView view) {
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
        return (command == MenuView.SELECTED.EXIT);
    }

    @Override
    public boolean run() {
        try {
            draw();
            if (processKey()) {
                game.setGameState(new MenuState(game, new MainMenuView()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
