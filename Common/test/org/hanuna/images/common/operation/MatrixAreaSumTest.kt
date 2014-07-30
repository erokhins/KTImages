package org.hanuna.images.common.operation

import org.junit.Test
import org.hanuna.images.common.test.matrix
import org.hanuna.images.common.operations.createSumGetter
import org.hanuna.images.common.MutableMatrix
import org.hanuna.images.common.test.IntRingOperation
import kotlin.test.assertEquals
import org.hanuna.images.common.coord
import org.hanuna.images.common.rangeTo
import org.hanuna.images.common.test.MatrixBuilder
import org.hanuna.images.common.operations.ImageMatrixAreaSumGetter

/**
 * Created by smevok on 7/30/14.
 */

class MatrixAreaSumTest {

    class MutableIntMatrix(override val cols: Int, override val rows: Int): MutableMatrix<Int> {
        val array = IntArray(cols * rows)

        private fun toIndex(col: Int, row: Int) = row * cols + col

        override fun set(col: Int, row: Int, value: Int) {
            array[toIndex(col, row)] = value
        }
        override fun get(col: Int, row: Int): Int = array[toIndex(col, row)]
    }

    fun testGetter(f: MatrixBuilder<Int>.() -> Unit): ImageMatrixAreaSumGetter<Int> {
        val m = matrix(f)
        return m.createSumGetter(MutableIntMatrix(m.cols, m.rows), IntRingOperation)
    }

    Test fun simple() {
        val sumGetter = testGetter {
            row(1, 10)
            row(100, 1000)
        }
        assertEquals(1111, sumGetter[coord(0,0)..coord(1,1)])
        assertEquals(101, sumGetter[coord(0,0)..coord(0,1)])
        assertEquals(11, sumGetter[coord(0,0)..coord(1,0)])
        assertEquals(2412, sumGetter[coord(2,1)..coord(4,3)])
    }

    Test fun test23() {
        val sumGetter = testGetter {
            row(1, 5, 10)
            row(50, 100, 500)
        }
        assertEquals(2225, sumGetter[coord(1,0)..coord(3,2)])
        assertEquals(5, sumGetter[coord(4,3)..coord(4,3)])
        assertEquals(15, sumGetter[coord(3,3)..coord(4,3)])

        assertEquals(420, sumGetter[coord(1,0)..coord(1,7)])
        assertEquals(4475, sumGetter[coord(1,0)..coord(3,6)])

        assertEquals(420, sumGetter[coord(-5,-4)..coord(-5,3)])
        assertEquals(4475, sumGetter[coord(-5,-4)..coord(-3,2)])

        assertEquals(666, sumGetter[coord(3,2)..coord(5,3)])
        assertEquals(666, sumGetter[coord(3,-2)..coord(5,-1)])
        assertEquals(666, sumGetter[coord(-3,2)..coord(-1,3)])
        assertEquals(666, sumGetter[coord(-3,-2)..coord(-1,-1)])
        assertEquals(666, sumGetter[coord(-9,-6)..coord(-7,-5)])
    }
}