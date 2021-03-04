package Controller.State;

import Controller.Game;
import Model.*;
import View.ArenaView;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class InGameStateTest {
    private Game game;
    private Screen screenMock;

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
        game.setGameState(new InGameState(game));
    }

    @Test
    public void PauseStateTransition() throws IOException {
        when(game.getGui().getCommand()).thenReturn(ArenaView.COMMAND.ESC);
        game.getGameState().run();
        assertEquals(PauseState.class, game.getGameState().getClass());
    }

    @Test
    public void GameOverStateTransition() throws IOException {
        when(game.getGui().getCommand()).thenReturn(ArenaView.COMMAND.NULL);
        when(game.getArena().gameEnded()).thenReturn(true);
        game.getGameState().run();
        assertEquals(GameOverState.class, game.getGameState().getClass());
    }
}
