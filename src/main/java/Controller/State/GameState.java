package Controller.State;

import Controller.Game;
import View.State.MenuView;

import java.io.IOException;

public abstract class GameState {
    protected Game game;

    public GameState(Game game) {
        this.game = game;
    }

    public void draw() throws IOException { }

    public boolean processKey() throws IOException { return false; }

    public abstract boolean run();
}
