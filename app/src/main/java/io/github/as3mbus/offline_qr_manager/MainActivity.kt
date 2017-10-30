package io.github.as3mbus.offline_qr_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.view.View
import com.google.zxing.WriterException
import android.R.attr.bitmap





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(View.OnClickListener {
            val EditTextValue = editText.text.toString()

            try {
                var bitmap = TextToImageEncode(EditTextValue)

                imageView.setImageBitmap(bitmap)

            } catch (e: WriterException) {
                e.printStackTrace()
            }


        })
    }

    @Throws(WriterException::class)
    fun TextToImageEncode(Value: String): Bitmap? {
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
                    resources.getColor(R.color.QRCodeBlackColor)
                else
                    resources.getColor(R.color.QRCodeWhiteColor)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}
