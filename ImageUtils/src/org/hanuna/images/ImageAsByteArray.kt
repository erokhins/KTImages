package org.hanuna.images

import org.hanuna.images.common.TwoDimension
import org.hanuna.images.common.ImageMutableMatrix
import org.hanuna.images.utils.ImageMatrixContainer
import org.hanuna.images.common.to255Int
import org.hanuna.images.common.Matrix
import org.hanuna.images.common.writeTo

/**
 * Created by smevok on 8/20/14.
 */


public enum class ByteArrayType(
        val pixelStride: Int,
        val redOffset: Int,
        val greenOffset: Int,
        val blueOffset: Int,
        val alphaOffset: Int
) {
    val hasAlpha = alphaOffset >= 0
    val bandOffsets
            = if (hasAlpha)
        intArray(redOffset, greenOffset, blueOffset, alphaOffset)
    else
        intArray(redOffset, greenOffset, blueOffset)

    BGR: ByteArrayType(3, 2, 1, 0, -1)
    RGB: ByteArrayType(3, 0, 1, 2, -1)
    ABGR: ByteArrayType(4, 3, 2, 1, 0)
    GRAY: ByteArrayType(1, 0, 0, 0, -1)
}

public trait ImageAsByteArray: TwoDimension {
    val byteArray: ByteArray
    val arrayType: ByteArrayType

    protected val Int.redIndex: Int get() = this * arrayType.pixelStride + arrayType.redOffset
    protected val Int.greenIndex: Int get() = this * arrayType.pixelStride + arrayType.greenOffset
    protected val Int.blueIndex: Int get() = this * arrayType.pixelStride + arrayType.blueOffset
    protected val Int.alphaIndex: Int
        get() {
            assert(arrayType.hasAlpha)
            return this * arrayType.pixelStride + arrayType.alphaOffset
        }
}

public class GrayImage(override val cols: Int, override val rows: Int) : ImageAsByteArray, ImageMatrixContainer<Int>() {
    override val byteArray: ByteArray = ByteArray(arraySize)
    override val arrayType: ByteArrayType = ByteArrayType.GRAY

    override fun getInArray(index: Int): Int = byteArray[index].to255Int()
    override fun setInArray(index: Int, value: Int) {
        byteArray[index] = value.toByte()
    }
}

public class Image(
        override val cols: Int,
        override val rows: Int,
        override val arrayType: ByteArrayType,
        override val byteArray: ByteArray = ByteArray(cols * rows * 3)
) : ImageAsByteArray, ImageMatrixContainer<Pixel>() {
    { assert(arrayType.pixelStride == 3 && !arrayType.hasAlpha) }

    override fun getInArray(index: Int): Pixel {
        return object : Pixel {
            override val red: Int = byteArray[index.redIndex].to255Int()
            override val green: Int = byteArray[index.greenIndex].to255Int()
            override val blue: Int = byteArray[index.blueIndex].to255Int()
        }
    }
    override fun setInArray(index: Int, value: Pixel) {
        byteArray[index.redIndex] = value.red.toByte()
        byteArray[index.greenIndex] = value.green.toByte()
        byteArray[index.blueIndex] = value.blue.toByte()
    }
}

public class AImage(
        override val cols: Int,
        override val rows: Int,
        override val arrayType: ByteArrayType,
        override val byteArray: ByteArray = ByteArray(cols * rows * 3)
) : ImageAsByteArray, ImageMatrixContainer<APixel>() {
    { assert(arrayType.pixelStride == 4 && arrayType.hasAlpha) }

    override fun getInArray(index: Int): APixel
            = object : APixel {
        override val red: Int = byteArray[index.redIndex].to255Int()
        override val green: Int = byteArray[index.greenIndex].to255Int()
        override val blue: Int = byteArray[index.blueIndex].to255Int()
        override val alpha: Int = byteArray[index.alphaIndex].to255Int()
    }

    override fun setInArray(index: Int, value: APixel) {
        byteArray[index.redIndex] = value.red.toByte()
        byteArray[index.greenIndex] = value.green.toByte()
        byteArray[index.blueIndex] = value.blue.toByte()
        byteArray[index.alphaIndex] = value.alpha.toByte()
    }
}

public fun Matrix<Int>.save(): GrayImage {
    val image = GrayImage(cols, rows)
    writeTo(image)
    return image
}

public fun Matrix<Pixel>.save(): Image {
    val image = Image(cols, rows, ByteArrayType.BGR)
    writeTo(image)
    return image
}

public fun Matrix<APixel>.save(): AImage {
    val image = AImage(cols, rows, ByteArrayType.ABGR)
    writeTo(image)
    return image
}

