package Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void incrementTest() {
        Car c = new Car(new Position(5, 5), ArenaModel.COLOR.RED, Car.ORIENTATION.DOWN, '|');
        Player player = new Player(c);
        player.incrementPoints();
        assertEquals(1, player.getPoints());

        player.incrementPoints();
        player.incrementPoints();

        assertEquals(3, player.getPoints());
    }
}
