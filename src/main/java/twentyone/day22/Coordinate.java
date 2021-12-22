package twentyone.day22;

public record Coordinate(int x, int y, int z) {
    @Override
    public String toString() {
        return x + ","+ y + "," + z;
    }
}
