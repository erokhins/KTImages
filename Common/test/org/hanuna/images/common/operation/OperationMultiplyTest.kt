package org.hanuna.images.common.operation

import org.junit.Test
import org.hanuna.images.common
import org.hanuna.images.common.operations
import org.hanuna.images.common.test.toStr

public class OperationMultiplyTest {
    Test fun vectorMultiply() {
        val v1 = common.test.vector(1, 2, 3)
        val v2 = common.test.vector(2, 3, 9)
        val result = operations.vectorMultiplyOperation(v1, v2, {this * it }, {this + it})
        test.assertEquals(35, result)
    }

    Test fun matrixMultiply() {
        val m1 = common.test.matrix<Int> {
            row(1, 2, 3, 4)
            row(5, 6, 7, 8)
        }
        val m2 = common.test.matrix<Int> {
            row(1, 2, 1)
            row(3, 4, 5)
            row(5, 6, 5)
            row(7, 8, 9)
        }
        val result = operations.matrixMultiplyOperation(m1, m2, {this * it }, { this  + it})
        test.assertEquals("50:60:62 114:140:142", result.toStr())
    }

}