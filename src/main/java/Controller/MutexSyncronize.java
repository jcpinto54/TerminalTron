package Controller;


public class MutexSyncronize {
    private static boolean locked = false;

    public static void lock() {
        while(locked);
        locked = true;
    }
    public static synchronized void unlock() {
        locked = false;
    }
}
