package View;

import Controller.Game;
import View.State.HighScoresView;
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

public class HighScoresViewTest {
    Screen screenMock;
    HighScoresView highScoresView;
    Game gameMock;

    @Before
    public void init() throws IOException {
        this.screenMock = Mockito.mock(Screen.class);
        this.highScoresView = new HighScoresView();
        this.gameMock = Mockito.mock(Game.class);
        when(gameMock.getScreen()).thenReturn(screenMock);
    }

    @Test
    public void getKeyTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.EXIT, highScoresView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Escape));
        assertEquals(MenuView.SELECTED.EXIT, highScoresView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        assertEquals(MenuView.SELECTED.NULL, highScoresView.getKey(gameMock));
    }
}
