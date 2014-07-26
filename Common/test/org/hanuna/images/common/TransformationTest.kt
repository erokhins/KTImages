package org.hanuna.images.common

import ogr.hanuna.images.common.Matrix
import ogr.hanuna.images.common.forAll2
import ogr.hanuna.images.common.ForAllStrategy
import org.junit.Test
import kotlin.test.assertEquals
import ogr.hanuna.images.common.simpleTransform
import ogr.hanuna.images.common.SimpleTransform
import ogr.hanuna.images.common.SimpleTransform.*


/**
 * Created by smevok on 7/27/14.
 */
public class TransformationTest {
    // 012
    // 345
    val matrix: Matrix<Int> = object: Matrix<Int> {
        override fun get(col: Int, row: Int): Int {
            return 3 * row + col
        }
        override val cols: Int = 3
        override val rows: Int = 2
    }

    fun Matrix<Int>.toStr(): String {
        val s = StringBuilder()
        forAll2(ForAllStrategy.LR_TD) { (col, row) ->
            if (col == 0 && row != 0)
                s.append(":")
            s.append(this[col, row])
        }

        return s.toString()
    }

    Test fun strTest() {
        assertEquals("012:345", matrix.toStr())
    }

    fun runTest(expected: String, transform: SimpleTransform) {
        assertEquals(expected, matrix.simpleTransform(transform).toStr())
    }

    Test fun IDTest() {
        runTest("012:345", ID)
    }
    Test fun Rotate90Test() {
        runTest("30:41:52", ROTATE90)
    }
    Test fun Rotate180Test() {
        runTest("543:210", ROTATE180)
    }
    Test fun Rotate270Test() {
        runTest("25:14:03", ROTATE270)
    }

    Test fun SymHorTest() {
        runTest("345:012", SYM_HOR)
    }
    Test fun SymVertTest() {
        runTest("210:543", SYM_VERT)
    }
    Test fun SymDiagTest() {
        runTest("03:14:25", SYM_DIAG)
    }
    Test fun SymDiag2Test() {
        runTest("52:41:30", SYM_DIAG2)
    }


}