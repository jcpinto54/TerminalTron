package Controller.State;

import Controller.Game;
import Model.*;
import View.ArenaView;
import View.State.MenuView;
import View.State.PauseView;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PauseStateTest {
    private Game game;
    private Screen screenMock;
    PauseView PauseViewMock;

    @Before
    public void init() {
        this.screenMock = Mockito.mock(Screen.class);
        this.game = new Game(100, 50, screenMock);
        ArenaView arenaViewMock = Mockito.mock(ArenaView.class);
        this.game.setGui(arenaViewMock);
        ArenaModel arenaModelMock = Mockito.mock(ArenaModel.class);
        this.game.setArena(arenaModelMock);

        Timer timerMock = Mockito.mock(Timer.class);
        when(arenaModelMock.getTimer()).thenReturn(timerMock);

        this.PauseViewMock = Mockito.mock(PauseView.class);
        game.setGameState(new PauseState(game, PauseViewMock));
    }

    @Test
    public void InGameTransition() throws IOException {
        when(PauseViewMock.getKey(game)).thenReturn(MenuView.SELECTED.ENTER);
        game.getGameState().run();
        assertEquals(InGameState.class, game.getGameState().getClass());
    }

    @Test
    public void MainMenuTransition() throws IOException {
        when(PauseViewMock.getKey(game)).thenReturn(MenuView.SELECTED.EXIT);
        game.getGameState().run();
        assertEquals(MenuState.class, game.getGameState().getClass());
    }
}
