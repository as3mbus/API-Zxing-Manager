package io.github.as3mbus.QRManager

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_redeem.*

class RedeemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        val bundle = intent.extras

        val redeemActivate = bundle.getBoolean("redeemActivate")
        if (redeemActivate) {
            toolbar.setTitle(R.string.redeem_title)
            actionButton.text = resources.getText(R.string.redeem_button)

        } else {
            LongOperation().execute(bundle.getString("outlet","ABC"))
            toolbar.setTitle(R.string.activate_title)
            actionButton.text = resources.getText(R.string.activate_button)


        }
        outletTextView.text = bundle.getString("outlet")

    }

    private inner class LongOperation : AsyncTask<String, Int, Bitmap>() {
        override fun doInBackground(vararg p0: String?): Bitmap? {
            val param =p0[0]
            var bitmap:Bitmap?=null
            try {
                bitmap = QRGenerator.TextToImageEncode(param!!)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
            return bitmap
        }


        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            actionImageView.setImageBitmap(result)
        }

    }
}
