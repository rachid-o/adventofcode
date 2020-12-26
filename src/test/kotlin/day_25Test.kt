package `2020`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class day_25Test {

    @Test
    fun calcPublicKey() {
        assertEquals(5764801, calcPublicKey(7, 8))
        assertEquals(14897079, calcPublicKey(17807724, 8))
        assertEquals(14897079, calcPublicKey(5764801, 11))
    }

    @Test
    fun determineLoopSize() {
        assertEquals(8, determineLoopSize(7, 5764801))
        assertEquals(11, determineLoopSize(7, 17807724))
    }

    @Test
    fun calcEnryptionKey() {
        assertEquals(14897079, calcEnryptionKey(5764801, 17807724))
        assertEquals(14897079, calcEnryptionKey(17807724, 5764801))
    }

}
