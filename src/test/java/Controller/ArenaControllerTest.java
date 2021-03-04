package Controller;

import Model.*;
import View.ArenaView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ArenaControllerTest {
    private Game mockGame;
    private ArenaView mockView;
    private ArenaModel mockArena;
    private PlayerPositionUpdater mockUpdater;


    @Before
    public void setUp() {
        mockArena = Mockito.mock(ArenaModel.class);
        mockView = Mockito.mock(ArenaView.class);
        mockGame = Mockito.mock(Game.class);
        Timer mockTimer = Mockito.mock(Timer.class);
        when(mockArena.getTimer()).thenReturn(mockTimer);
        when(mockGame.getArena()).thenReturn(mockArena);
        when(mockGame.getGui()).thenReturn(mockView);
        mockUpdater = Mockito.mock(PlayerPositionUpdater.class);

    }

    @Test
    public void testUp() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.UP1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('^', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    //Tests opposite turn
    @Test
    public void testUpFromDown() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.DOWN, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.UP1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('v', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    @Test
    public void testRight() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.UP, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.RIGHT1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('>', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    //Tests opposite turn
    @Test
    public void testRightFromLeft() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.LEFT, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.RIGHT1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('<', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    @Test
    public void testLeft() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.UP, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.LEFT1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();

        assertEquals('<', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    //Tests opposite turn
    @Test
    public void testLeftFromRight() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.RIGHT1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('>', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    @Test
    public void testDown() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.LEFT, '|');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.DOWN1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('v', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    //Tests opposite turn
    @Test
    public void testDownFromUp() throws IOException, InterruptedException {
        ArenaController controller = new ArenaController(mockGame, mockUpdater);

        Car c = new Car(new Position(1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.UP, 'O');

        when(mockView.getCommand()).thenReturn(ArenaView.COMMAND.DOWN1);
        when(mockArena.getCar(1)).thenReturn(c);
        controller.start();
        assertEquals('^', mockArena.getCar(1).getOrientation().getRepresentation());
    }

    @Test
    public void testGameEnded() {

    }
}
