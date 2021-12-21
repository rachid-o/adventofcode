package twentyone.day20.grid;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

public class Grid {

    private final int[][] grid;

    public Grid(int[][] newGrid) {
        this.grid = newGrid;
    }

    public Grid(List<List<Integer>> input) {
        int rowSize = input.size();
        int colSize = input.get(0).size();
        grid = new int[rowSize][colSize];
        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(0).size(); col++) {
                grid[row][col] = input.get(row).get(col);
            }
        }
    }

    public Grid update(Map<Point, Integer> newGridPoints) {
        int height = grid.length;
        int width = grid[0].length;
        int[][] newGrid = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                newGrid[row][col] = newGridPoints.get(new Point(row, col));
            }
        }
        return new Grid(newGrid);
    }

    public Grid expand(int pad, int bgPixel) {
        int height = grid.length + 2 * pad;
        int width = grid[0].length + 2 * pad;
        int[][] newGrid = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    newGrid[row][col] = bgPixel;
                } else {
                    newGrid[row][col] = grid[row - 1][col - 1];
                }
            }
        }
        return new Grid(newGrid);
    }

    public Integer getValue(Point p) {
        return grid[p.row()][p.col()];
    }

    public List<Integer> getSquare(Point p, Integer fallback) {
        return Stream.of(
                        new Point(p.row() - 1, p.col() - 1),
                        new Point(p.row() - 1, p.col()),
                        new Point(p.row() - 1, p.col() + 1),
                        new Point(p.row(), p.col() - 1),
                        new Point(p.row(), p.col()),
                        new Point(p.row(), p.col() + 1),
                        new Point(p.row() + 1, p.col() - 1),
                        new Point(p.row() + 1, p.col()),
                        new Point(p.row() + 1, p.col() + 1)
                )
                .map(pt -> {
                    if (inGrid(pt)) {
                        return getValue(pt);
                    } else {
                        return fallback;
                    }
                })
                .toList();
    }

    public boolean inGrid(Point pt) {
        return inGrid(pt, 0);
    }

    public boolean inGrid(Point pt, int pad) {
        return pt.row() >= pad && pt.row() < grid.length - pad && pt.col() >= pad && pt.col() < grid[0].length - pad;
    }


    public Map<Point, Integer> getAll() {
        var allPoints = range(0, grid.length)
                .mapToObj(rowIndex -> range(0, grid[rowIndex].length)
                        .mapToObj(colIndex -> new Point(rowIndex, colIndex))
                )
                .flatMap(i -> i)
                .collect(Collectors.toMap(p -> p, this::getValue));

        return allPoints;
    }

    @Override
    public String toString() {
        return stream(grid).map(line ->
                        stream(line).mapToObj(this::toPixel)
                                .collect(joining(""))
                )
                .collect(joining("\n"));
    }

    private String toPixel(int c) {
        if (c == 0) {
            return ".";
        } else if (c == 1) {
            return "#";
        } else {
            throw new IllegalArgumentException(c + " is invalid, maybe it should be: " + (char) c);
        }
    }
}


