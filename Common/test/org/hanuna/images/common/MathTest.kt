package org.hanuna.images.common

import org.junit.Test
import kotlin.test.assertEquals
import org.hanuna.math.myMod
import org.hanuna.math.to255Int
import org.hanuna.math.toCorrectImageNumber

/**
 * Created by smevok on 7/26/14.
 */

class MathTest {
    Test fun modTest() {
        assertEquals(2, 5.myMod(3))
        assertEquals(1, (-5).myMod(3))
        assertEquals(1, (-2).myMod(3))
        assertEquals(2, 2.myMod(3))
    }

    Test fun to255Int() {
        var a: Byte = -1
        assertEquals(255, a.to255Int())

        a = 13
        assertEquals(13, a.to255Int())
    }

    Test fun toCorrectNumber() {
        assertEquals(0, (-1).toCorrectImageNumber(5))
        assertEquals(4, (5).toCorrectImageNumber(5))
        assertEquals(3, (-7).toCorrectImageNumber(5))
        assertEquals(1, (11).toCorrectImageNumber(5))
    }
}
