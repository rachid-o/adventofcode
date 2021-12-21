package twentyone;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.nio.file.Path.of;
import static twentyone.Constants.resourcePath;
import static util.logging.Logger.log;

public class Day18 {

    void run() throws IOException {
        var inputFilename = resourcePath + "day_18.txt";
        log("Java version: " + System.getProperty("java.version"));
        log("Reading input from: " + inputFilename);
        var input = parseInput(inputFilename);
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
                .toList();
    }

    private String parseLine(String line) {
        System.out.println("\t" + line);
        for(char c: line.toCharArray()) {
            System.out.println("c: " + c);
        }
        return line;
    }

    public static void main(String[] args) throws Exception {
        new Day18().run();
    }
}
