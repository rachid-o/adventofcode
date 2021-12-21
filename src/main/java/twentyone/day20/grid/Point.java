package twentyone.day20.grid;

public record Point(int row, int col) {
    @Override
    public String toString() {
        return row + "," + col;
    }
}
