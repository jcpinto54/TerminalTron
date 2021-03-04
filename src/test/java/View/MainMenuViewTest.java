package View;

import Controller.Game;
import View.State.MainMenuView;
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

public class MainMenuViewTest {
    Screen screenMock;
    MainMenuView mainMenuView;
    Game gameMock;

    @Before
    public void init() throws IOException {
        this.screenMock = Mockito.mock(Screen.class);
        this.mainMenuView = new MainMenuView();
        this.gameMock = Mockito.mock(Game.class);
        when(gameMock.getScreen()).thenReturn(screenMock);
    }

    @Test
    public void getKeyTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.ENTER, mainMenuView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        mainMenuView.getKey(gameMock);
        mainMenuView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        mainMenuView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.OPTIONS, mainMenuView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        mainMenuView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.HIGHSCORES, mainMenuView.getKey(gameMock));

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        mainMenuView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.EXIT, mainMenuView.getKey(gameMock));
    }
}
