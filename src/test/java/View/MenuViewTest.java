package View;

import Controller.Game;
import View.State.MainMenuView;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MenuViewTest {
    Screen screenMock;
    MainMenuView mainMenuView;
    Game gameMock;

    @Before
    public void init() {
        this.screenMock = Mockito.mock(Screen.class);
        this.mainMenuView = new MainMenuView();
        this.gameMock = Mockito.mock(Game.class);
        when(gameMock.getScreen()).thenReturn(screenMock);
    }

    @Test
    public void moveUpSelectedTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        mainMenuView.getKey(gameMock);

        assertEquals(3, mainMenuView.getSelected());

        mainMenuView.getKey(gameMock);
        mainMenuView.getKey(gameMock);

        assertEquals(1 , mainMenuView.getSelected());

        mainMenuView.getKey(gameMock);

        assertEquals(0, mainMenuView.getSelected());
    }

    @Test
    public void moveDownSelectedTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        mainMenuView.getKey(gameMock);

        assertEquals(1, mainMenuView.getSelected());

        mainMenuView.getKey(gameMock);
        mainMenuView.getKey(gameMock);

        assertEquals(3, mainMenuView.getSelected());

        mainMenuView.getKey(gameMock);

        assertEquals(0, mainMenuView.getSelected());
    }

    @Test
    public void moveSelectedTest() throws IOException {
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        mainMenuView.getKey(gameMock);
        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        mainMenuView.getKey(gameMock);

        assertEquals(0, mainMenuView.getSelected());

        mainMenuView.getKey(gameMock);
        mainMenuView.getKey(gameMock);
        mainMenuView.getKey(gameMock);
        mainMenuView.getKey(gameMock);

        assertEquals(0, mainMenuView.getSelected());

        when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        mainMenuView.getKey(gameMock);

        assertEquals(1, mainMenuView.getSelected());
    }
}
