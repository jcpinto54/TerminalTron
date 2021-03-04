package Controller;

import Controller.State.GameState;
import Controller.State.MenuState;
import View.ArenaView;
import Model.ArenaModel;
import View.State.MainMenuView;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Game {
    private GameState gameState;
    private Screen screen;
    private int width, height;
    private ArenaModel arena;
    private ArenaView gui;

    public Game(int width, int height, Screen screen) {
        this.width = width;
        this.height = height;
        this.gameState = new MenuState(this, new MainMenuView());
        this.screen = screen;
        this.arena = new ArenaModel(this.width, this.height);
        this.gui = new ArenaView(this.screen);
        this.arena.addObserver(this.gui);
    }

    public void resetGame() {
        this.arena.removeObserver(this.gui);
        this.arena = new ArenaModel(this.width, this.height);
        this.gui = new ArenaView(this.screen);
        this.arena.addObserver(this.gui);
    }

    public void start() throws IOException {
        while(gameState.run());
        screen.close();
    }

    public void setArena(ArenaModel arena) {
        this.arena = arena;
    }

    public void setGui(ArenaView gui) {
        this.gui = gui;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Screen getScreen() {
        return screen;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArenaModel getArena() {
        return arena;
    }

    public ArenaView getGui() {
        return gui;
    }
}
