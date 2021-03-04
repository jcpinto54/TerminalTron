package Controller.State;

import Controller.Game;
import View.State.MainMenuView;
import View.State.MenuView;
import View.State.OptionsView;

import java.io.IOException;

public class OptionsState extends GameState {
    OptionsView view;

    public OptionsState(Game game, OptionsView view) {
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
                game.getArena().getPlayers().get(0).getCar().setWallChar(view.getPlayer1Char());
                game.getArena().getPlayers().get(1).getCar().setWallChar(view.getPlayer2Char());
                game.getArena().getPlayers().get(0).getCar().setColor(view.getPlayer1Color());
                game.getArena().getPlayers().get(1).getCar().setColor(view.getPlayer2Color());
            case EXIT:
                return true;
        }
        return false;
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
