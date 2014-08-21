package org.hanuna.images

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.WildcardFileFilter
import org.apache.commons.io.IOCase
import org.apache.commons.io.FilenameUtils
import javax.imageio.ImageWriteParam
import javax.imageio.stream.FileImageOutputStream
import javax.imageio.IIOImage

/**
 * Created by smevok on 8/17/14.
 */

public fun readBufferImage(filename: String): BufferedImage = ImageIO.read(File(filename))!!

public fun File.readBufferedImage(): BufferedImage = ImageIO.read(this)!!
public fun File.readImage(): Image = readBufferedImage().toImage()

public fun getAllFiles(directory: String, vararg mask: String): Collection<File> {
    val dir = File(directory)
    return FileUtils.listFiles(dir, WildcardFileFilter(mask, IOCase.INSENSITIVE), null)
}

public val File.baseName: String
    get() = FilenameUtils.getBaseName(getName())!!

public trait ImageWriter {
    fun doWrite(image: BufferedImage, dstFile: File)
    class object {
        public val PNG: PNGWriter = PNGWriter
        public fun JPEG(quality: Float): JPEGWriter = JPEGWriter(quality)
    }
}

public object PNGWriter : ImageWriter {
    override fun doWrite(image: BufferedImage, dstFile: File) {
        ImageIO.write(image, "png", dstFile)
    }
}

public class JPEGWriter(val quality: Float) : ImageWriter {
    override fun doWrite(image: BufferedImage, dstFile: File) {
        val writer = ImageIO.getImageWritersByFormatName("jpeg").next()
        val param = writer.getDefaultWriteParam()
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
        param.setCompressionQuality(quality)

        val output = FileImageOutputStream(dstFile)
        writer.setOutput(output)
        val iioImage = IIOImage(image, null, null)
        writer.write(null, iioImage, param)
    }
}

private fun detectWriter(filename: String): ImageWriter {
    return when (FilenameUtils.getExtension(filename)) {
        "jpg", "jpeg" -> JPEGWriter(0.97f)
        "png" -> PNGWriter
        else -> PNGWriter
    }
}

public fun BufferedImage.writeToFile(filename: String, writer: ImageWriter = detectWriter(filename)) {
    writer.doWrite(this, File(filename))
}

public fun ImageAsByteArray.writeToFile(filename: String, writer: ImageWriter = detectWriter(filename)) {
    asBufferedImage().writeToFile(filename, writer)
}