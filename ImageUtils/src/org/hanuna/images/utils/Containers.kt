package org.hanuna.images.utils

import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.ImageMutableMatrix

/**
 * Created by smevok on 8/11/14.
 */


public abstract class ImageContainer<T> : ImageMutableMatrix<T> {

    protected val arraySize: Int = cols * rows
    protected abstract fun getInArray(index: Int): T
    protected abstract fun setInArray(index: Int, value: T)

    private fun indexInArray(col: Int, row: Int) = col + row * cols

    override fun get_correct(col: Int, row: Int): T = getInArray(indexInArray(col, row))
    override fun set_correct(col: Int, row: Int, value: T) = setInArray(indexInArray(col, row), value)
}

public class DoubleImageContainer(override val cols: Int, override val rows: Int): ImageContainer<Double>() {
    val array = DoubleArray(arraySize)

    override fun getInArray(index: Int): Double = array[index]
    override fun setInArray(index: Int, value: Double) {
        array[index]= value
    }
}

public class LongImageContainer(override val cols: Int, override val rows: Int): ImageContainer<Long>() {
    val array = LongArray(arraySize)

    override fun getInArray(index: Int): Long = array[index]
    override fun setInArray(index: Int, value: Long) {
        array[index]= value
    }
}

public class FloatImageContainer(override val cols: Int, override val rows: Int): ImageContainer<Float>() {
    val array = FloatArray(arraySize)

    override fun getInArray(index: Int): Float = array[index]
    override fun setInArray(index: Int, value: Float) {
        array[index]= value
    }
}

public class IntImageContainer(override val cols: Int, override val rows: Int): ImageContainer<Int>() {
    val array = IntArray(arraySize)

    override fun getInArray(index: Int): Int = array[index]
    override fun setInArray(index: Int, value: Int) {
        array[index]= value
    }
}

public class ShortImageContainer(override val cols: Int, override val rows: Int): ImageContainer<Short>() {
    val array = ShortArray(arraySize)

    override fun getInArray(index: Int): Short = array[index]
    override fun setInArray(index: Int, value: Short) {
        array[index]= value
    }
}

public class ByteImageContainer(override val cols: Int, override val rows: Int): ImageContainer<Byte>() {
    val array = ByteArray(arraySize)

    override fun getInArray(index: Int): Byte = array[index]
    override fun setInArray(index: Int, value: Byte) {
        array[index]= value
    }
}