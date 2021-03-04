package Controller;

import Model.*;
import View.ArenaView;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class PlayerPositionUpdaterTest {
    private ArenaModel arena;
    private ArenaView mockGui;
    private Game mockGame;
    private PlayerPositionUpdater playerUpdater;

    @Before
    public void init() {
        arena = new ArenaModel(80, 24);
        mockGui = Mockito.mock(ArenaView.class);
        mockGame = Mockito.mock(Game.class);
        when(mockGame.getArena()).thenReturn(arena);
        when(mockGame.getGui()).thenReturn(mockGui);
        playerUpdater = new PlayerPositionUpdater(arena);
        playerUpdater.testModeOn();
    }

    @Test
    public void carMove() {
        playerUpdater.advance(arena.getPlayers().get(0));
        playerUpdater.advance(arena.getPlayers().get(1));

        Position player1oldPos = new Position(1, arena.getHeight()/2);
        Position player1newPos = new Position(2, arena.getHeight()/2);
        Position player2oldPos = new Position(arena.getWidth()-2, arena.getHeight()/2);
        Position player2newPos = new Position(arena.getWidth()-3, arena.getHeight()/2);
        assertEquals(player1newPos, arena.getCar(1).getPosition());
        assertEquals(player2newPos, arena.getCar(2).getPosition());
        assertEquals(new Wall(player1oldPos, ArenaModel.COLOR.GREEN), arena.getCar(1).getWalls().get(0));
        assertEquals(new Wall(player2oldPos, ArenaModel.COLOR.RED), arena.getCar(2).getWalls().get(0));
    }

    @Test
    public void noCollisionTest() {
        Pair<ArenaController.COLLISION, Player> result = playerUpdater.checkCollision(arena.getCar(1), arena);

        assertNull(result.getValue());
        assertEquals(ArenaController.COLLISION.NO, result.getKey());


        playerUpdater.processCollision(arena.getCar(1), result);
        Car c = new Car(new Position(1, arena.getHeight()/2), ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, 'O');
        assertEquals(c, arena.getCar(1));
        assertEquals(c.getLives(), arena.getCar(1).getLives());

    }

    @Test
    public void collisionWallTest() {
        Position car1Pos = arena.getCar(1).getPosition();

        Wall w = new Wall(car1Pos, ArenaModel.COLOR.DARKGREY);
        arena.addWall(w);
        Pair<ArenaController.COLLISION, Player> result = playerUpdater.checkCollision(arena.getCar(1), arena);

        assertNull(result.getValue());
        assertEquals(ArenaController.COLLISION.WALL, result.getKey());

        Car c = new Car(new Position(1, arena.getHeight()/2), ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, 'O');
        playerUpdater.processCollision(arena.getCar(1), result);

        c.loseLife();
        assertEquals(c, arena.getCar(1));
        assertEquals(c.getLives(), arena.getCar(1).getLives());
    }

    @Test
    public void collisionCarTest() {
        Position car1Pos = arena.getCar(1).getPosition();

        Car c = new Car(car1Pos, ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, '|');
        c.addWall(new Wall(new Position(6, 9), c.getColor()));
        c.addWall(new Wall(new Position(9, 9), c.getColor()));
        arena.addPlayer(c);
        Pair<ArenaController.COLLISION, Player> result = playerUpdater.checkCollision(arena.getCar(1), arena);

        assertEquals(ArenaController.COLLISION.CAR, result.getKey());
        assertEquals(c, result.getValue().getCar());

        Car cClone = new Car(car1Pos, ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, '|');
        Car clone = new Car(new Position(1, arena.getHeight()/2), ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, 'O');
        playerUpdater.processCollision(arena.getCar(1), result);

        cClone.loseLife();
        cClone.getWalls().clear();
        clone.loseLife();
        clone.getWalls().clear();
        assertEquals(clone, arena.getCar(1));
        assertEquals(clone.getLives(), arena.getCar(1).getLives());
        assertEquals(cClone, c);
        assertEquals(cClone.getLives(), c.getLives());
    }
}
