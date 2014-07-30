package ogr.hanuna.images.common.operations

import ogr.hanuna.images.common.Vector
import ogr.hanuna.images.common.MutableVector
import ogr.hanuna.images.common.forAll
import ogr.hanuna.images.common.equalSize

/**
 * Created by smevok on 7/30/14.
 */

public trait ImageVectorIntervalSumGetter<T> {
    public fun get(range: IntRange): T
}

public fun <T> Vector<T>.createSumGetter(forPartialSums: MutableVector<T>, sumOperation: SumOperation<T>):
        ImageVectorIntervalSumGetter<T> {
    assert(this.equalSize(forPartialSums))

    calculatePartialSum(forPartialSums, sumOperation.plus)
    val sumGetter = Image2SumGetter(forPartialSums, sumOperation)
    return ImageVectorIntervalSumGetterImpl(sumGetter, sumOperation.minus)
}

private fun <T> Vector<T>.calculatePartialSum(forPartialSums: MutableVector<T>, plus: T.(T) -> T) {
    var sum = this[0]
    forAll {
        if (it != 0)
            sum = sum + this[it]
        forPartialSums[it] = sum
    }
}

private class Image2SumGetter<T>(private val partialSums: Vector<T>, sumOperation: SumOperation<T>) {
    private val plus: T.(T) -> T = sumOperation.plus
    private val minus: T.(T) -> T = sumOperation.minus
    private val times: Int.(T) -> T = sumOperation.nTimes
    private val size = partialSums.size
    private val allSum = partialSums[size - 1]

    val period = 2 * size

    fun get(index: Int): T {
        assert(index >=0)
        val count2Sum = (index + 1) / (2 * size)
        val tail = index - count2Sum * 2 * size
        var sum = count2Sum * 2 * allSum
        if (tail < 0) // for example: index = 2 * size - 1
            return sum
        if (tail < size)
            return sum + partialSums[tail]
        else
            return sum + 2 * allSum - partialSums[2 * size - tail - 2]
    }
}

private class ImageVectorIntervalSumGetterImpl<T>(private val sumGetter: Image2SumGetter<T>,
                                                  private val minus: T.(T) -> T) : ImageVectorIntervalSumGetter<T> {
    override fun get(range: IntRange): T {
        val offset: Int
        if (range.start > 0)
            offset = 0
        else {
            val t = (- range.start) / sumGetter.period
            offset = (t + 1) * sumGetter.period
        }
        return sumGetter[range.end + offset] - sumGetter[range.start + offset - 1]
    }
}