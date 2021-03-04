package View.State;

import Controller.Game;

import java.io.IOException;

public abstract class MenuView {
    protected int selected;
    public enum SELECTED {ENTER, OPTIONS, HIGHSCORES, EXIT, NULL}

    MenuView() {
        selected = 0;
    }

    public abstract void draw(Game game) throws IOException;

    public abstract SELECTED getKey(Game game) throws IOException;

    public int getSelected() {
        return selected;
    }

    void moveUpSelected(int maxOptions) {
        selected--;
        selected %= maxOptions;
        if (selected < 0) selected = maxOptions + selected;
    }

    void moveDownSelected(int maxOptions) {
        selected++;
        selected %= maxOptions;
    }
}
