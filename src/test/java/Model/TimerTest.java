package Model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimerTest {
    private Timer toTest = new Timer();

    @Test
    public void timeDiffInSecondsTest() throws InterruptedException {
        Date tMinus1 = new Date();
        Thread.sleep(1000);
        Date t = new Date();
        assertEquals(1, toTest.timeDiffInSeconds(t, tMinus1));
        assertEquals(1, toTest.timeDiffInSeconds(tMinus1, t));
    }

    @Test
    public void testGetTime() throws InterruptedException {
        toTest.start();

        Thread.sleep(1000);
        toTest.update();

        assertEquals("00:01", toTest.getTime());

        Thread.sleep(2000);
        toTest.update();

        assertEquals("00:03", toTest.getTime());

        toTest.stop();
    }

    @Test
    public void testStoppedTimer() throws InterruptedException {
        String initTime = toTest.getTime();
        Thread.sleep(1500);
        assertEquals(initTime, toTest.getTime());
    }
}