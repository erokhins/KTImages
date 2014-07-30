package org.hanuna.images.common

import org.junit.Test
import org.hanuna.images.common.test.vector
import org.hanuna.images.common.vectorMultiplyOperation
import kotlin.test.assertEquals
import org.hanuna.images.common.test.matrix
import org.hanuna.images.common.matrixMultiplyOperation
import org.hanuna.images.common.test.toStr

/**
 * Created by smevok on 7/27/14.
 */
public class OperationMultiplyTest {
    Test fun vectorMultiply() {
        val v1 = vector(1, 2, 3)
        val v2 = vector(2, 3, 9)
        val result = vectorMultiplyOperation(v1, v2, {(a,b)-> a * b}, {(a,b)-> a + b})
        assertEquals(35, result)
    }

    Test fun matrixMultiply() {
        val m1 = matrix<Int> {
            row(1,2,3,4)
            row(5,6,7,8)
        }
        val m2 = matrix<Int> {
            row(1,2,1)
            row(3,4,5)
            row(5,6,5)
            row(7,8,9)
        }
        val result = matrixMultiplyOperation(m1, m2, {(a,b)-> a * b}, {(a,b)-> a + b})
        assertEquals("50:60:62 114:140:142", result.toStr())
    }

}