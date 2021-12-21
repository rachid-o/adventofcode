package twentyone.day20;

import kotlin.Pair;
import twentyone.day20.grid.Grid;
import twentyone.day20.grid.Point;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Path.of;
import static java.util.Arrays.stream;
import static twentyone.Constants.resourcePath;
import static util.AoCUtils.checkAnswer;
import static util.logging.Logger.log;

public class Day20 {

    void run() throws IOException {
        var inputFilename = resourcePath + "day_20.txt";
//        var inputFilename = resourcePath + "day_20.test.txt";
        log("Reading input from: " + inputFilename);
        var input = parseInput(inputFilename);
        var algorithm = input.getFirst();
        var image = input.getSecond();

        long answer1 = part1(algorithm, image, 2);
        log("Answer of part 1: " + answer1);
        long answer2 = part1(algorithm, image, 50);
        log("Answer of part 2: " + answer2);
//        checkAnswer(answer1, 35);
        checkAnswer(answer1, 5583);
//        checkAnswer(answer2, 3351);
        checkAnswer(answer2, 19592);
    }

    private long part1(List<Integer> algorithm, List<List<Integer>> image, int iterations) {
        var enhancedGrid = new Grid(image);
        for(int i = 1; i <= iterations; i++) {
            int bgPixel;
            if(i % 2 == 0) {
                bgPixel = algorithm.get(0);
            } else {
                bgPixel = fromPixel('.');
            }
            enhancedGrid = enhance(enhancedGrid, algorithm, bgPixel);
        }
        return enhancedGrid.getAll()
                .values().stream()
                .filter(v -> v == 1)
                .count();
    }

    private Grid enhance(Grid grid, List<Integer> algorithm, int bgPixel) {
        var expandedGrid = grid.expand(1, bgPixel);
        var newGridPoints = expandedGrid.getAll().keySet().stream()
                .collect(Collectors.toMap(
                p -> p,
                p -> determineOutput(expandedGrid, algorithm, p, bgPixel)
        ));
        return expandedGrid.update(newGridPoints);
    }

    private Integer determineOutput(Grid grid, List<Integer> algorithm, Point point, int fallback) {
        var neighbors = grid.getSquare(point, fallback);
        var binary = neighbors.stream()
                .map( nr -> Integer.toString(nr))
                .collect(Collectors.joining(""));

        var decimal = new BigInteger(binary, 2).intValue();
        return algorithm.get(decimal);
    }

    private Pair<List<Integer>, List<List<Integer>>> parseInput(String inputFilename) throws IOException {
        var parts = Files
                .readString(of(inputFilename))
                .split("\n\n");
        assert parts.length >= 2;
        var strAlgo = parts[0];
        var strInputImage = parts[1];

        var algorithm = strAlgo.chars()
                .mapToObj(this::fromPixel)
                .toList();
        
        var inputImage = stream(strInputImage.split("\n"))
            .map( line -> line.chars()
                    .mapToObj(this::fromPixel)
                    .collect(Collectors.toList())
            )
            .toList();

        return new Pair(algorithm, inputImage);
    }

    private int fromPixel(int c) {
        if(c == '#') {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        new Day20().run();
    }
}
