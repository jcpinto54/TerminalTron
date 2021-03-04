package Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArenaModelTest {

    @Test
    public void changePlayerCarTest() {
        ArenaModel arena = new ArenaModel(10, 10);

        Car c = new Car(new Position(5,5), ArenaModel.COLOR.BLUE, Car.ORIENTATION.RIGHT, '|');
        arena.setCar(1, c);

        assertEquals(c, arena.getCar(1));

    }

    @Test
    public void addWallTest() {
        ArenaModel arena = new ArenaModel(80, 24);

        Wall w1 = new Wall(new Position(0,0), ArenaModel.COLOR.DARKGREY);
        Wall w2 = new Wall(new Position(1,0), ArenaModel.COLOR.DARKGREY);
        Wall w3 = new Wall(new Position(41,0), ArenaModel.COLOR.DARKGREY);

        assertEquals(arena.getWalls().get(0), w1);
        assertEquals(arena.getWalls().get(2), w2);
        assertEquals(arena.getWalls().get(arena.getWidth() + 2), w3);
    }

    @Test
    public void gameEnded() {
        ArenaModel arena = new ArenaModel(10, 10);

        while(arena.getCar(1).isAlive())
            arena.getCar(1).loseLife();

        assertEquals(0, arena.getCar(1).getLives());
        assertEquals(true, arena.gameEnded());

        arena.setCar(1, new Car(new Position( 1, 1), ArenaModel.COLOR.GREEN, Car.ORIENTATION.RIGHT, '|'));

        while (arena.getCar(2).isAlive()) {
            arena.getCar(2).loseLife();
        }

        assertEquals(0, arena.getCar(2).getLives());
        assertEquals(true, arena.gameEnded());
    }

    @Test
    public void getWinner() {
        ArenaModel arena = new ArenaModel(10, 10);

        while(arena.getCar(1).isAlive())
            arena.getCar(1).loseLife();

        assertEquals(2, arena.getWinner());

        while(arena.getCar(2).isAlive())
            arena.getCar(2).loseLife();

        assertEquals(-1, arena.getWinner());
    }
}
