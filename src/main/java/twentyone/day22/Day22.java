package twentyone.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.nio.file.Path.of;
import static twentyone.Constants.resourcePath;
import static util.AoCUtils.checkAnswer;
import static util.logging.Logger.log;
import static util.logging.Logger.println;

public class Day22 {

    void run() throws IOException {
        var inputFilename = resourcePath + "day_22.txt";
//        var inputFilename = resourcePath + "day_22.test.txt";
        log("Reading input from: " + inputFilename);
        var input = parseInput(inputFilename);
        log("Input size: " + input.size());
//        input.forEach(System.out::println);

        long answer1 = part1(input);
        log("Answer of part 1: " + answer1);
//        checkAnswer(answer1, 590784);
        checkAnswer(answer1, 607573);
//        var input2 = parseInput(resourcePath + "day_22.part2.txt");
//        long answer2 = part1(input2);
//        log("Answer of part 2: " + answer2);
//        checkAnswer(answer2, 2758514936282235L);
    }

    private long part1(List<Instruction> input) {
        Set<Coordinate> onCubes = new HashSet<>(Integer.MAX_VALUE);
        int stepCounter = 1;
        for(var ins: input) {
            println("Processing "+stepCounter+":\t " + ins);
            stepCounter++;
            for(int x = ins.x().from(); x <= ins.x().to(); x++) {
                if(x < -50 || x > 50) continue;
                for(int y = ins.y().from(); y <= ins.y().to(); y++) {
                    if(y < -50 || y > 50) continue;
                    for(int z = ins.z().from(); z <= ins.z().to(); z++) {
                        if(z < -50 || z > 50) continue;
                        var c = new Coordinate(x, y, z);
                        if(ins.isOn()) {
                            onCubes.add(c);
                        } else {
                            onCubes.remove(c);
                        }
                    }
                }
            }

        }
        return onCubes.size();
    }

    private List<Instruction> parseInput(String inputFilename) throws IOException {
        var instructions = Files
                .lines(of(inputFilename))
                .map( line -> {
//                    println(line);
                    var strInstruction = line.split(" ");
                    var isOn = strInstruction[0].equals("on");
                    Range xRange = null;
                    Range yRange = null;
                    Range zRange = null;
                    for(var strRangeForAxis : strInstruction[1].split(",")) {
//                        println(" strRangeForAxis: " + strRangeForAxis);
                        var coordRange = strRangeForAxis.split("=");
                        var axis = coordRange[0];
//                        println("\taxis: `" + axis+"`");
                        var strRange = coordRange[1].split("\\.\\.");
//                        println("\tcoordRange[1]: `" + coordRange[1]+"`");
//                        println("\tstrRange: " + Arrays.toString(strRange));
                        var range = new Range(parseInt(strRange[0]), parseInt(strRange[1]));
                        if(axis.equals("x")) {
                            xRange = range;
                        } else if(axis.equals("y")) {
                            yRange = range;
                        } else if(axis.equals("z")) {
                            zRange = range;
                        } else {
                            throw new IllegalArgumentException("Can't parse unknown axis: " + axis);
                        }
                    }
                    return new Instruction(isOn, xRange, yRange, zRange);
                }).toList();

        return instructions;
    }


    public static void main(String[] args) throws Exception {
        new Day22().run();
    }
}
