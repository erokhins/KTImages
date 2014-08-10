package org.hanuna.images

import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.ImageMatrixContainer
import org.hanuna.images.common.to255Int
import org.hanuna.images.common.equalSize

/**
 * Created by smevok on 8/11/14.
 */

public trait Pixel {
    // 0..255
    val red: Int
    val green: Int
    val blue: Int
}

public trait Image : ImageMatrix<Pixel>

public val Image.redChanel: ImageMatrix<Int>
    get() = object : ImageMatrix<Int> {
        override val cols: Int = this@redChanel.cols
        override val rows: Int = this@redChanel.rows
        override fun get_correct(col: Int, row: Int): Int = this@redChanel[col, row].red
    }

public val Image.greenChanel: ImageMatrix<Int>
    get() = object : ImageMatrix<Int> {
        override val cols: Int = this@greenChanel.cols
        override val rows: Int = this@greenChanel.rows
        override fun get_correct(col: Int, row: Int): Int = this@greenChanel[col, row].green
    }

public val Image.blueChanel: ImageMatrix<Int>
    get() = object : ImageMatrix<Int> {
        override val cols: Int = this@blueChanel.cols
        override val rows: Int = this@blueChanel.rows
        override fun get_correct(col: Int, row: Int): Int = this@blueChanel[col, row].blue
    }

public class ImageContainer(override val cols: Int, override val rows: Int): ImageMatrixContainer<Pixel>() {
    public val array: ByteArray = ByteArray(3 * arraySize)

    override fun getInArray(index: Int): Pixel = object : Pixel {
        override val red: Int = array[index * 3].to255Int()
        override val green: Int = array[index * 3 + 1].to255Int()
        override val blue: Int = array[index * 3 + 2].to255Int()
    }
    override fun setInArray(index: Int, value: Pixel) {
        array[index * 3] = value.red.toByte()
        array[index * 3 + 1] = value.green.toByte()
        array[index * 3 + 2] = value.blue.toByte()
    }
}

public fun composite(redChannel: ImageMatrix<Int>, greenChanel: ImageMatrix<Int>, blueChannel: ImageMatrix<Int>): Image
        = object : Image {
    {
        assert(redChannel.equalSize(greenChanel), redChannel.equalSize(blueChannel))
    }
    override val cols: Int = redChannel.cols
    override val rows: Int = redChannel.rows
    override fun get_correct(col: Int, row: Int): Pixel = object : Pixel {
        override val red: Int = redChannel[col, row]
        override val green: Int = greenChanel[col, row]
        override val blue: Int = blueChannel[col, row]
    }
}