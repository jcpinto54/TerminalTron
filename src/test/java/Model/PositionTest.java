package Model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    Position p;

    @Before
    public void init() {
        p = new Position(0, 0);
    }

    @Test
    public void directions() {
        assertEquals(new Position(0, -1), p.up());
        assertEquals(new Position(0, 1), p.down());
        assertEquals(new Position(-1, 0), p.left());
        assertEquals(new Position(1, 0), p.right());
    }
}
