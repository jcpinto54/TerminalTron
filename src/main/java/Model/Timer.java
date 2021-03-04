package Model;

import java.util.Date;

public class Timer {
    private long seconds = 0;
    private long minutes = 0;
    private boolean running = false;
    private Date lastDate;

    public Timer() {}

    public void stop() {
        this.running = false;
    }

    public void start() {
        this.running = true;
        if (lastDate == null) lastDate = new Date();
    }

    public void update() {
        if (running) {
            Date now = new Date();
            long secondsToAdd = timeDiffInSeconds(now, lastDate);
            if (secondsToAdd >= 1) {
                seconds += secondsToAdd;
                if (seconds >= 60) {
                    minutes++;
                    seconds %= 60;
                }
                lastDate = now;
            }
        } else {
            lastDate = new Date();
        }
    }

    public long timeDiffInSeconds(Date t1, Date t2) {
        if (t1.before(t2)) {
            Date tmp = t1;
            t1 = t2;
            t2 = tmp;
        }
        long diff = t1.getTime() - t2.getTime();
        return diff / 1000 % 60;
    }

    public String getTime() {
        String minStr;
        if (minutes < 10) {
            minStr = "0" +  minutes;
        }
        else minStr = String.valueOf(minutes);

        String secStr;
        if (seconds < 10) {
            secStr = "0" +  seconds;
        }
        else secStr = String.valueOf(seconds);

        return minStr + ":" + secStr;
    }

    public void reset() {
        running = false;
        seconds = 0;
        minutes = 0;
    }
}
