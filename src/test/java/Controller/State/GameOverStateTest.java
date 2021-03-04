package Controller.State;

import Controller.Game;
import Controller.ScoresController;
import Model.*;
import View.ArenaView;
import View.State.GameOverView;
import View.State.MenuView;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GameOverStateTest {
    private Game game;
    private Screen screenMock;
    GameOverView GameOVerViewMock;
    ScoresController scoresControllerMock;

    @Before
    public void init() {
        this.screenMock = Mockito.mock(Screen.class);
        this.game = new Game(100, 50, screenMock);
        ArenaView arenaViewMock = Mockito.mock(ArenaView.class);
        this.game.setGui(arenaViewMock);
        ArenaModel arenaModelMock = Mockito.mock(ArenaModel.class);
        this.game.setArena(arenaModelMock);

        List<Player> players = new ArrayList<>();
        Car c1 = new Car(new Position(5, 5), ArenaModel.COLOR.RED, Car.ORIENTATION.RIGHT, 'O');
        Car c2 = new Car(new Position(25, 25), ArenaModel.COLOR.GREEN, Car.ORIENTATION.LEFT, '|');
        players.add(new Player(c1));
        players.add(new Player(c2));
        when(game.getArena().getPlayers()).thenReturn(players);

        this.GameOVerViewMock = Mockito.mock(GameOverView.class);
        this.scoresControllerMock = Mockito.mock(ScoresController.class);
        game.setGameState(new GameOverState(game, GameOVerViewMock, scoresControllerMock));
    }

    @Test
    public void MainMenuTransition() throws IOException {
        when(GameOVerViewMock.getKey(game)).thenReturn(MenuView.SELECTED.EXIT);
        when(game.getArena().getWinner()).thenReturn(1);
        game.getGameState().run();
        assertEquals(MenuState.class, game.getGameState().getClass());
    }

    @Test
    public void StayingGameOver() throws IOException {
        when(GameOVerViewMock.getKey(game)).thenReturn(MenuView.SELECTED.NULL);
        game.getGameState().run();
        assertEquals(GameOverState.class, game.getGameState().getClass());
    }
}
