package org.hanuna.images.common.operation

import ogr.hanuna.images.common.operations.RingOperation
import org.junit.Test
import org.hanuna.images.common.test.vector
import ogr.hanuna.images.common.MutableVector
import ogr.hanuna.images.common.operations.createSumGetter
import kotlin.test.assertEquals
import ogr.hanuna.images.common.operations.ImageVectorIntervalSumGetter

/**
 * Created by smevok on 7/30/14.
 */
public class VectorIntervalSumTest {

    object IntRingOperation : RingOperation<Int> {
        override val minus: Int.(Int) -> Int = { this - it }
        override val plus: Int.(Int) -> Int = { this + it }
        override val nTimes: Int.(Int) -> Int = { this * it }
        override val times: Int.(Int) -> Int = { this * it }
    }

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