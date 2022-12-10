package utils


data class Grid<T>(val input: List<List<T>>) {

    private val area: List<MutableList<T>> = input.map { it.toMutableList() }

    data class Point(val row: Int, val col: Int) {
        override fun toString() = "$row,$col"
    }

    fun update(p: Point, newValue: T) {
        area[p.row][p.col] = newValue
    }

    fun getValue(p: Point) = area[p.row][p.col]

    fun getAllNeighbors(p: Point) = getEdgeNeighborPoints(p) + getCornerNeighborPoints(p)

    fun getEdgeNeighborPoints(p: Point) = listOf(
        Point(p.row - 1, p.col),
        Point(p.row, p.col - 1),
        Point(p.row, p.col + 1),
        Point(p.row + 1, p.col),
    )
        .filter { it.row in this.area.indices && it.col in this.area[0].indices }

    fun getCornerNeighborPoints(p: Point) = listOf(
        Point(p.row - 1, p.col - 1),
        Point(p.row - 1, p.col + 1),
        Point(p.row + 1, p.col - 1),
        Point(p.row + 1, p.col + 1),
    )
        .filter { it.row in this.area.indices && it.col in this.area[0].indices }


    fun getAll(): Map<Point, T> {
        val allPoints = mutableMapOf<Point, T>()
        area.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                allPoints[Point(rowIndex, colIndex)] = value
            }
        }
        return allPoints
    }

    // or just use getAll() ?
    fun forEach(action: (Point, T) -> Unit) =
        area.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                action(Point(rowIndex, colIndex), value)
            }
        }

    override fun toString() = this.area.joinToString("\n") { it.joinToString("") }

    fun getHeight(): Int = this.area.first().size
    fun getWidth(): Int = this.area.size

    companion object {

        fun <T> fromGrid(grid: Grid<T>, defaultValue: T): Grid<T> {
            return create(grid.getHeight(), grid.getWidth(), defaultValue)
        }

        fun <T> create(height: Int, width: Int, defaultValue: T) : Grid<T> {
            val rows = (0 until height).map { _ ->
                (0 until width).map { defaultValue }
            }
            return Grid(rows)
        }
    }
}
