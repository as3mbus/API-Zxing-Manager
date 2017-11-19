package io.github.as3mbus.QRManager

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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
            GenerateQR().execute(bundle.getString("code",""))
            toolbar.setTitle(R.string.activate_title)
            actionButton.text = resources.getText(R.string.activate_button)
            val success = bundle.getBoolean("success",false)
            val permission = bundle.getBoolean("permission",false)
            val isActivated = bundle.getBoolean("isActivated",false)
            if(success){
                if(!isActivated){
                    if(permission){
                        message1TextView.text = "The following Voucher will be activated and able to be redeemed in other outlet untill it expires on"
                        message2TextView.text = bundle.getString("expirydate","error").substring(0,10);
                    }
                    else {
                        message1TextView.text = "The following Voucher is not originated from this outlet, please activate Voucherfrom it's origin Outlet which is"
                        message2TextView.text = bundle.getString("outletName","error")
                    }
                }
                else{
                    message1TextView.text = "The following Voucher is already active"
                    message2TextView.text = ""
                }
            }
            else {
                message1TextView.text = "The following Voucher isn't valid"
                message2TextView.text = ""
            }


        }

    }

    private inner class GenerateQR : AsyncTask<String, Int, Bitmap>() {
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
