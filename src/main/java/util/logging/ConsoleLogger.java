package util.logging;

import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;
import static java.time.ZonedDateTime.now;

public class ConsoleLogger implements System.Logger {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String getName() {
        return "ConsoleLogger";
    }

    @Override
    public boolean isLoggable(Level level) {
        return true;
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        if(level.getSeverity() > Level.INFO.getSeverity()) {
            System.err.printf("%s - %s - %s%n", dtf.format(now()), msg, thrown);
        } else {
            System.out.printf("%s - %s - %s%n", dtf.format(now()), msg, thrown);
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        if(level.getSeverity() > Level.INFO.getSeverity()) {
            System.err.printf("%s - %s%n", dtf.format(now()), format(format, params));
        } else {
            System.out.printf("%s - %s%n", dtf.format(now()), format(format, params));
        }
    }

}