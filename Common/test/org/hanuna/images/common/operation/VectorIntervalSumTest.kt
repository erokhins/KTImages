package org.hanuna.images.common.operation

import org.hanuna.images.common.operations.RingOperation
import org.junit.Test
import org.hanuna.images.common.test.vector
import org.hanuna.images.common.MutableVector
import org.hanuna.images.common.operations.createSumGetter
import kotlin.test.assertEquals
import org.hanuna.images.common.operations.ImageVectorIntervalSumGetter
import org.hanuna.images.common.test.IntRingOperation

/**
 * Created by smevok on 7/30/14.
 */
public class VectorIntervalSumTest {

    class MutableIntVector(override val size: Int) : MutableVector<Int> {
        val values = IntArray(size)

        override fun set(index: Int, value: Int) {
            values[index] = value
        }
        override fun get(index: Int): Int = values[index]
    }

    fun IntArray.toArray(): Array<Int> = Array(size, {this[it]})

    fun sumGetter(vararg i: Int) : ImageVectorIntervalSumGetter<Int> {
        val vector = vector(*i.toArray())
        return vector.createSumGetter(MutableIntVector(vector.size), IntRingOperation)
    }

    Test fun simple() {
        val sumGetter = sumGetter(1,2,3,-2)
        assertEquals(3, sumGetter[0..1])
        assertEquals(4, sumGetter[-1..1])
        assertEquals(-4, sumGetter[3..4])
        assertEquals(5, sumGetter[-3..-2])
    }

    Test fun smallVector() {
        val sumGetter = sumGetter(1)
        assertEquals(5, sumGetter[-3..1])
    }
}