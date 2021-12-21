package util;

import java.text.MessageFormat;

import static java.text.MessageFormat.format;

public class AoCUtils {

    public static void checkAnswer(Number actual, Number expected) {
        if(actual.longValue() != expected.longValue()) {
            throw new IllegalArgumentException(format(
                    "{0} is incorrect, should be: {1}",
                    actual, expected));
        }
    }

    public static void checkAnswer(Number answer, boolean correct) {
        if(!correct) {
            throw new IllegalArgumentException(answer + " is incorrect");
        }
    }

}
