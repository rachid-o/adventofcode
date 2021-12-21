package util.logging;

import static java.lang.System.Logger.Level.INFO;

public class Logger {
    static System.Logger log = System.getLogger("2021");

    public static void log(Object msg) {
        log.log(INFO, msg);
    }
    public static void println(Object msg) {
        System.out.println(msg);
    }

}
