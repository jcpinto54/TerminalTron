package Controller.State;

import Controller.Game;
import Model.*;
import View.ArenaView;
import View.State.HighScoresView;
import View.State.MenuView;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HighScoresTest {
    private Game game;
    private Screen screenMock;
    HighScoresView HighScoresViewMock;

    @Before
    public void init() {
        this.screenMock = Mockito.mock(Screen.class);
        this.game = new Game(100, 50, screenMock);
        ArenaView arenaViewMock = Mockito.mock(ArenaView.class);
        this.game.setGui(arenaViewMock);
        ArenaModel arenaModelMock = Mockito.mock(ArenaModel.class);
        this.game.setArena(arenaModelMock);
        this.HighScoresViewMock = Mockito.mock(HighScoresView.class);
        game.setGameState(new HighScoresState(game, HighScoresViewMock));
    }

    @Test
    public void MainMenuTransition() throws IOException {
        when(HighScoresViewMock.getKey(game)).thenReturn(MenuView.SELECTED.EXIT);
        game.getGameState().run();
        assertEquals(MenuState.class, game.getGameState().getClass());
    }
}
