package Controller;

import Controller.State.GameState;
import Controller.State.MenuState;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;


public class GameTest {
    Screen screenMock;
    Game game;

    @Before
    public void init() {
        this.screenMock = Mockito.mock(Screen.class);
        this.game = new Game(100, 50, screenMock);
    }
    @Test
    public void testGame() throws IOException {
        GameState stateMock = Mockito.mock(MenuState.class);
        game.setGameState(stateMock);
        game.start();

        when(stateMock.run()).thenReturn(false);
        verify(stateMock, times(1)).run();
        verify(screenMock, times(1)).close();
    }

    @Test
    public void resetGameTest() {
    }
}
