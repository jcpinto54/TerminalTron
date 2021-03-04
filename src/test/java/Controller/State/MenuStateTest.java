package Controller.State;

import Controller.Game;
import Model.*;
import View.ArenaView;
import View.State.MainMenuView;
import View.State.MenuView;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MenuStateTest {
    private Game game;
    private Screen screenMock;
    MainMenuView mainMenuViewMock;

    @Before
    public void init() {
        this.screenMock = Mockito.mock(Screen.class);
        this.game = new Game(100, 50, screenMock);
        ArenaView arenaViewMock = Mockito.mock(ArenaView.class);
        this.game.setGui(arenaViewMock);
        ArenaModel arenaModelMock = Mockito.mock(ArenaModel.class);
        this.game.setArena(arenaModelMock);

        this.mainMenuViewMock = Mockito.mock(MainMenuView.class);
        game.setGameState(new MenuState(game, mainMenuViewMock));
    }

    @Test
    public void InGameTransition() throws IOException {
        when(mainMenuViewMock.getKey(game)).thenReturn(MenuView.SELECTED.ENTER);
        game.getGameState().run();
        assertEquals(InGameState.class, game.getGameState().getClass());
    }

    @Test
    public void OptionsTransition() throws IOException {
        when(mainMenuViewMock.getKey(game)).thenReturn(MenuView.SELECTED.OPTIONS);
        game.getGameState().run();
        assertEquals(OptionsState.class, game.getGameState().getClass());
    }

    @Test
    public void HighScoresTransition() throws IOException {
        when(mainMenuViewMock.getKey(game)).thenReturn(MenuView.SELECTED.HIGHSCORES);
        game.getGameState().run();
        assertEquals(HighScoresState.class, game.getGameState().getClass());
    }

    @Test
    public void ExitTransition() throws IOException {
        when(mainMenuViewMock.getKey(game)).thenReturn(MenuView.SELECTED.EXIT);
        game.start();
        verify(screenMock, times(1)).close();
    }
}
