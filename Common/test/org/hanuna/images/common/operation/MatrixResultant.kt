package org.hanuna.images.common.operation

import org.junit.Test
import org.hanuna.images.common.test.matrix
import org.hanuna.images.common.operations.matrixResultant
import org.hanuna.images.common.test.toStr
import kotlin.test.assertEquals

/**
 * Created by smevok on 8/11/14.
 */
public class MatrixResultant {

    Test fun simple() {
        val big = matrix<Int> {
            row(1, 2, 3, 4)
            row(3, 5, 7, 9)
            row(-4, -3, -2, -1)
        }
        val small = matrix<Int> {
            row(1, 3)
            row(-1, 0)
        }
        val result = matrixResultant(big, small, {this * it}, {this + it}).toStr()
        assertEquals("4:6:8:7 22:29:36:37 -9:-6:-3:-3", result)
    }
}