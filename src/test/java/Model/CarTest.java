package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CarTest {
    private Car c;

    @Before
    public void init() {
        c = new Car(new Position(0, 0), ArenaModel.COLOR.BLUE, Car.ORIENTATION.RIGHT, '|');
    }

    @Test
    public void carLives() {
        int initLives = this.c.getLives();
        for (int i = 0; i < initLives; i++) {
            c.loseLife();
        }
        assertFalse(c.isAlive());
    }
}
