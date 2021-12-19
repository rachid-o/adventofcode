package twentyone;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.lang.System.Logger.Level.INFO;
import static java.nio.file.Path.of;
import static java.util.stream.Collectors.toList;

public class Day18 {

    private static final String resourcePath = "src/main/resources/2021";
    private static System.Logger log = System.getLogger("Day18");

    void run() throws IOException {
        var inputFilename = resourcePath + "/day_18.txt";
        log("Java version: " + System.getProperty("java.version"));
        log("Reading input from: " + inputFilename);
        var input = parseInput(inputFilename);
//        List<String> input = Files.lines(of(inputFilename)).toList();
        log("input size: " + input.size());
        input.forEach(line -> log("line: " + line));

        System.out.println("\n\n");
        log(parseLine("[1,2]"));

    }

    @NotNull
    private List<String> parseInput(String inputFilename) throws IOException {
        return Files
                .lines(of(inputFilename))
                .map(this::parseLine)
                .collect(toList());
    }

    private String parseLine(String line) {
        System.out.println("\t" + line);
        for(char c: line.toCharArray()) {
            System.out.println("c: " + c);
        }
        return "";
    }

    public static void log(String msg) {
        log.log(INFO, msg);
    }

    public static void main(String[] args) throws Exception {
        new Day18().run();
    }
}
