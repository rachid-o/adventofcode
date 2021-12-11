package utils


class Grid(input: List<List<Int>>) {

    private val area: List<MutableList<Int>> = input.map { it.toMutableList() }

    data class Point(val row: Int, val col: Int) {
        override fun toString() = "$row,$col"
    }

    fun update(p: Point, newValue: Int) {
        area[p.row][p.col] = newValue
    }

    fun getValue(p: Point) = area[p.row][p.col]

    fun getAllNeighbors(p: Point) = edgeNeighborPoints(p) + cornerNeighborPoints(p)

    fun edgeNeighborPoints(p: Point) = listOf(
        Point(p.row - 1, p.col),
        Point(p.row, p.col - 1),
        Point(p.row, p.col + 1),
        Point(p.row + 1, p.col),
    )
        .filter { it.row in this.area.indices && it.col in this.area[0].indices }

    fun cornerNeighborPoints(p: Point) = listOf(
        Point(p.row - 1, p.col - 1),
        Point(p.row - 1, p.col + 1),
        Point(p.row + 1, p.col - 1),
        Point(p.row + 1, p.col + 1),
    )
        .filter { it.row in this.area.indices && it.col in this.area[0].indices }


    fun getAll(): Map<Point, Int> {
        val allPoints = mutableMapOf<Point, Int>()
        area.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                allPoints[Point(rowIndex, colIndex)] = value
            }
        }
        return allPoints
    }

    // or just use getAll() ?
    fun forEach(action: (Point, Int) -> Unit) =
        area.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                action(Point(rowIndex, colIndex), value)
            }
        }

    override fun toString() = this.area.toGridString()
}
