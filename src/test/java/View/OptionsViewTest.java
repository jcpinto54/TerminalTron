package View;

import Controller.Game;
import View.State.MenuView;
import View.State.OptionsView;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class OptionsViewTest {
    Screen screenMock;
    OptionsView optionsView;
    Game gameMock;

    @Before
    public void init() throws IOException {
        this.screenMock = Mockito.mock(Screen.class);
        this.optionsView = new OptionsView();
        this.gameMock = Mockito.mock(Game.class);
        when(gameMock.getScreen()).thenReturn(screenMock);
    }

    @Test
    public void getKeyTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.ENTER, optionsView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        optionsView.getKey(gameMock);
        optionsView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.ENTER, optionsView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Escape));
        assertEquals(MenuView.SELECTED.EXIT, optionsView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        optionsView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Escape));
        assertEquals(MenuView.SELECTED.EXIT, optionsView.getKey(gameMock));
    }
}
