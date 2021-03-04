package View;

import Controller.Game;
import View.State.GameOverView;
import View.State.MenuView;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GameOverViewTest {
    Screen screenMock;
    GameOverView gameOverView;
    Game gameMock;

    @Before
    public void init() throws IOException {
        this.screenMock = Mockito.mock(Screen.class);
        this.gameOverView = new GameOverView();
        this.gameMock = Mockito.mock(Game.class);
        when(gameMock.getScreen()).thenReturn(screenMock);
    }

    @Test
    public void getKeyTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.EXIT, gameOverView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        assertEquals(MenuView.SELECTED.NULL, gameOverView.getKey(gameMock));

        gameOverView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.EXIT, gameOverView.getKey(gameMock));
    }
}
