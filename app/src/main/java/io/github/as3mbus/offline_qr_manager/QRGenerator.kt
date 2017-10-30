package io.github.as3mbus.offline_qr_manager

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

/**
 * Created by as3mbus on 30/10/17.
 */

class QRGenerator{
    companion object {
        @Throws(WriterException::class)
        public fun TextToImageEncode(Value: String): Bitmap? {
            val bitMatrix: BitMatrix
            try {
                bitMatrix = MultiFormatWriter().encode(
                        Value,
                        BarcodeFormat.QR_CODE,
                        500, 500, null
                )

            } catch (Illegalargumentexception: IllegalArgumentException) {

                return null
            }

            val bitMatrixWidth = bitMatrix.width

            val bitMatrixHeight = bitMatrix.height

            val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

            for (y in 0 until bitMatrixHeight) {
                val offset = y * bitMatrixWidth

                for (x in 0 until bitMatrixWidth) {

                    pixels[offset + x] = if (bitMatrix.get(x, y))
                        Color.parseColor("#000000")
                    else
                        Color.parseColor("#ffffff")
                }
            }
            val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

            bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
            return bitmap
        }
    }

}