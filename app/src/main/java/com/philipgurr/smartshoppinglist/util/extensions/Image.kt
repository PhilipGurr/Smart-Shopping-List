package com.philipgurr.smartshoppinglist.util.extensions

import android.graphics.*
import android.media.Image
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream

fun Image.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun YUV_420_888toNV21(image: ImageProxy): ByteArray {

    val width = image.width
    val height = image.height
    val ySize = width * height
    val uvSize = width * height / 4

    val nv21 = ByteArray(ySize + uvSize * 2)
    val yBuffer = image.planes[0].buffer
    val uBuffer = image.planes[1].buffer
    val vBuffer = image.planes[2].buffer

    var rowStride = image.planes[0].rowStride
    assert(image.planes[0].pixelStride == 1)

    var pos = 0

    if (rowStride == width) {
        yBuffer.get(nv21, 0, ySize)
        pos += ySize

    } else {
        var yBufferPos = (width - rowStride).toLong()
        while (pos < ySize) {
            yBufferPos = yBufferPos + (rowStride - width).toLong()
            yBuffer.position(yBufferPos.toInt())
            yBuffer.get(nv21, pos, width)
            pos += width
        }
    }

    rowStride = image.planes[2].rowStride
    val pixelStride = image.planes[2].pixelStride

    assert(rowStride == image.planes[1].rowStride)
    assert(pixelStride == image.planes[1].pixelStride)

    if (pixelStride == 2 && rowStride == width && uBuffer.get(0) == vBuffer.get(1)) {
        val savePixel = vBuffer.get(1)
        vBuffer.put(1, 0.toByte())
        if (uBuffer.get(0).toInt() == 0) {
            vBuffer.put(1, 255.toByte())
            if (uBuffer.get(0).toInt() == 255) {
                vBuffer.put(1, savePixel)
                vBuffer.get(nv21, ySize, uvSize)
                return nv21
            }
        }
        vBuffer.put(1, savePixel)
    }

    for (row in 0 until height / 2) {
        for (col in 0 until width / 2) {
            val vuPos = col * pixelStride + row * rowStride
            nv21[pos++] = vBuffer.get(vuPos)
            nv21[pos++] = uBuffer.get(vuPos)
        }
    }

    return nv21
}