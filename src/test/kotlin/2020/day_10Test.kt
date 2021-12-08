package `2020`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class day_10Test {

    @Test
    fun part2() {
        assertEquals(2, day10part2(listOf(1, 2)))
        assertEquals(4, day10part2(listOf(1, 2, 3)))
        assertEquals(7, day10part2(listOf(1, 2, 3, 4)))
        assertEquals(2, day10part2(listOf(1, 3, 6)))
        assertEquals(1, day10part2(listOf(1, 4, 7)))
        assertEquals(3, day10part2(listOf(1, 2, 4)))
        assertEquals(4, day10part2(listOf(1, 4, 5, 6, 7)))
    }

}
