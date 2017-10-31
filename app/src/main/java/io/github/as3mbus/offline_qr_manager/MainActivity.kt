package io.github.as3mbus.offline_qr_manager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            val EditTextValue = editText.text.toString()
            println(EditTextValue)
            try {
                val bitmap = QRGenerator.TextToImageEncode(EditTextValue)

                imageView.setImageBitmap(bitmap)

            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }
        scanButton.setOnClickListener{

            val intentIntegr= IntentIntegrator(this)
            intentIntegr.initiateScan()
        }



    }
    override fun onActivityResult(requestCode:Int, resultCode:Int, intent:Intent?) {
            val scanResult: IntentResult?
            scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            if (scanResult!= null)
                editText.setText(scanResult.contents)

        // else continue with any other code you need in the method

    }
}
