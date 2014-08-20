package org.hanuna.images

import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.ImageMatrixContainer
import org.hanuna.images.common.to255Int
import org.hanuna.images.common.equalSize
import org.hanuna.images.common.asImageMatrix

/**
 * Created by smevok on 8/11/14.
 */

public trait Pixel {
    // 0..255
    val red: Int
    val green: Int
    val blue: Int
}

public trait APixel : Pixel {
    val alpha: Int
}

fun pixel(red: Int, green: Int, blue: Int) : Pixel
    = object : Pixel {
    override val red: Int = red
    override val green: Int = green
    override val blue: Int = blue
}

fun pixel(red: Int, green: Int, blue: Int, alpha: Int): APixel
    = object : APixel {
    override val red: Int = red
    override val green: Int = green
    override val blue: Int = blue
    override val alpha: Int = alpha
}

public val ImageMatrix<Pixel>.redChanel: ImageMatrix<Int>
    get() = asImageMatrix { this@redChanel[col, row].red }

public val ImageMatrix<Pixel>.greenChannel: ImageMatrix<Int>
    get() = asImageMatrix { this@greenChannel[col, row].green }

public val ImageMatrix<Pixel>.blueChannel: ImageMatrix<Int>
    get() = asImageMatrix { this@blueChannel[col, row].blue }

public val ImageMatrix<APixel>.alphaChannel: ImageMatrix<Int>
    get() = asImageMatrix { this@alphaChannel[col, row].alpha }

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

public fun composite(redChannel: ImageMatrix<Int>, greenChannel: ImageMatrix<Int>, blueChannel: ImageMatrix<Int>): ImageMatrix<Pixel>
        = object : ImageMatrix<Pixel> {
    {
        assert(redChannel.equalSize(greenChannel), redChannel.equalSize(blueChannel))
    }
    override val cols: Int = redChannel.cols
    override val rows: Int = redChannel.rows
    override fun get_correct(col: Int, row: Int): Pixel = object : Pixel {
        override val red: Int = redChannel[col, row]
        override val green: Int = greenChannel[col, row]
        override val blue: Int = blueChannel[col, row]
    }
}