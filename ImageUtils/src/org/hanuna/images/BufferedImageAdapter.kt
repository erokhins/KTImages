package org.hanuna.images

import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.ComponentColorModel
import java.awt.color.ColorSpace
import java.awt.Transparency
import java.awt.image.DataBuffer
import java.awt.image.Raster
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.ImageMutableMatrix
import org.hanuna.images.common.TwoDimension
import org.hanuna.images.ByteArrayType

/**
 * Created by smevok on 8/17/14.
 */


public val BufferedImage.byteArrayType: ByteArrayType?
    get() {
       return when(getType()) {
            BufferedImage.TYPE_3BYTE_BGR -> ByteArrayType.BGR
            BufferedImage.TYPE_4BYTE_ABGR -> ByteArrayType.ABGR
            BufferedImage.TYPE_BYTE_GRAY -> ByteArrayType.GRAY
            else -> null
        }
    }

public val BufferedImage.imageAsByteArray: ImageAsByteArray?
    get() {
        val arrayType = byteArrayType
        val byteArray = getByteArray()
        if (arrayType != null && byteArray != null) {
            val cols = getWidth()
            val rows = getHeight()
            return object : ImageAsByteArray {
                override val cols: Int = cols
                override val rows: Int = rows
                override val byteArray: ByteArray = byteArray
                override val arrayType: ByteArrayType = arrayType
            }
        } else
            return null
    }

public fun BufferedImage.toImage(): Image {
    val image = imageAsByteArray
    if (image != null && image.arrayType.pixelStride == 3 && !image.arrayType.hasAlpha)
        return Image(image.cols, image.rows, image.arrayType, image.byteArray)

    val byteArray = toBGRByteArray()
    return Image(getWidth(), getHeight(), ByteArrayType.BGR, byteArray)
}

public fun ImageAsByteArray.asBufferedImage(): BufferedImage {
    val buffer = DataBufferByte(byteArray, byteArray.size)
    val cm = ComponentColorModel(
            ColorSpace.getInstance(ColorSpace.CS_sRGB)!!,
            arrayType.hasAlpha,
            false,
            if (arrayType.hasAlpha) Transparency.TRANSLUCENT else Transparency.OPAQUE,
            DataBuffer.TYPE_BYTE
    );

    val raster = Raster.createInterleavedRaster(
            buffer,
            cols,
            rows,
            cols * arrayType.pixelStride,
            arrayType.pixelStride,
            arrayType.bandOffsets,
            null
    )
    return BufferedImage(cm, raster, false, null)
}


private fun BufferedImage.getByteArray(): ByteArray? {
    val buffer = getRaster()?.getDataBuffer()
    if (buffer != null && buffer is DataBufferByte)
        return buffer.getData()
    else
        return null
}

public fun BufferedImage.toBGRByteArray(): ByteArray {
    val cols = getWidth()
    val rows = getHeight()
    val result = ByteArray(3 * cols * rows)
    for (col in 0..cols - 1) for(row in 0..rows - 1) {
        val pixelNumber = row * cols + col
        val offset = 3 * pixelNumber
        val pixel = getRGB(col, row)

        result[offset + 2] = (pixel ushr 16).toByte()
        result[offset + 1] = (pixel ushr 8).toByte()
        result[offset + 0] = (pixel ushr 0).toByte()
    }

    return result
}