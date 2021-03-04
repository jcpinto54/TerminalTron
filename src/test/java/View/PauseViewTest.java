package View;

import Controller.Game;
import Model.ArenaModel;
import Model.Timer;
import View.State.MenuView;
import View.State.PauseView;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PauseViewTest {
    Screen screenMock;
    PauseView pauseView;
    Game gameMock;

    @Before
    public void init() throws IOException {
        this.screenMock = Mockito.mock(Screen.class);
        this.pauseView = new PauseView();
        this.gameMock = Mockito.mock(Game.class);
        when(gameMock.getScreen()).thenReturn(screenMock);

        ArenaModel arenaModelMock = Mockito.mock(ArenaModel.class);
        when(gameMock.getArena()).thenReturn(arenaModelMock);
        Timer timerMock = Mockito.mock(Timer.class);
        when(arenaModelMock.getTimer()).thenReturn(timerMock);
    }

    @Test
    public void getKeyTest() throws IOException {
        when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.ENTER, pauseView.getKey(gameMock));

        when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        pauseView.getKey(gameMock);
        pauseView.getKey(gameMock);
        when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.ENTER, pauseView.getKey(gameMock));

        when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        pauseView.getKey(gameMock);
        when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuView.SELECTED.EXIT, pauseView.getKey(gameMock));
    }
}
