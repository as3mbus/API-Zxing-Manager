package io.github.as3mbus.offline_qr_manager

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            val EditTextValue = editText.text.toString()
            println(EditTextValue);
            try {
                var bitmap = QRGenerator.TextToImageEncode(EditTextValue)

                imageView.setImageBitmap(bitmap)

            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }
        scanButton.setOnClickListener{

            IntentIntegrator
        }
    }

}
