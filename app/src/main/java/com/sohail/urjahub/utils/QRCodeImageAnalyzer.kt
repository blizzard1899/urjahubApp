package com.sohail.urjahub.utils

import android.graphics.ImageFormat.*
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import com.sohail.urjahub.interfaces.QRCodeFoundListener
import java.nio.ByteBuffer
import kotlin.Result


class QRCodeImageAnalyzer(listener1: QRCodeFoundListener) : ImageAnalysis.Analyzer {

    private var listener: QRCodeFoundListener

    init {
        this.listener = listener1!!
    }


    override fun analyze(image: ImageProxy) {
        if (image.getFormat() == YUV_420_888 || image.getFormat() == YUV_422_888 || image.getFormat() == YUV_444_888) {
            val byteBuffer: ByteBuffer = image.planes[0].buffer
            val imageData = ByteArray(byteBuffer.capacity())
            byteBuffer.get(imageData)

            val source = PlanarYUVLuminanceSource(
                imageData,
                image.width, image.height,
                0, 0,
                image.width, image.height,
                false
            )

            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

            try {
                val result = QRCodeMultiReader().decode(binaryBitmap)
                listener.onQrCodeFound(result.getText())
            } catch (e: FormatException) {
                listener.QrCodeNotFound()
            } catch (e: ChecksumException) {
                listener.QrCodeNotFound()
            } catch (e: NotFoundException) {
                listener.QrCodeNotFound()
            }

        }
        image.close()
    }
}



