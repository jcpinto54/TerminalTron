package Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WallTest {
    private Wall w;

    @Test
    public void init() {
        w = new Wall(new Position(0, 0), ArenaModel.COLOR.DARKGREY);
        assertEquals("#111111", w.getColor().getStr());
    }
}
