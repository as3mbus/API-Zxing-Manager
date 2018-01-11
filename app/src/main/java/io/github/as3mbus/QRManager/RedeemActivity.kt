package io.github.as3mbus.QRManager

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.WriterException
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_redeem.*
import org.json.JSONObject


class RedeemActivity : AppCompatActivity() {
    var context: Context? = null

    var dialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        context = this.applicationContext

        dialog = Dialog(this)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_connecting)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        val bundle = intent.extras

        val activateRedeem = bundle.getBoolean("activateRedeem")
        if (activateRedeem)
            activateLookAndFeel(bundle)
        else
            redeemLookAndFeel(bundle)

    }

    // async task to generate QR Code
    @SuppressLint("StaticFieldLeak")
    private inner class GenerateQR : AsyncTask<String, Int, Bitmap>() {
        override fun doInBackground(vararg p0: String?): Bitmap? {
            val param = p0[0]
            var bitmap: Bitmap? = null
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

    //handle look and feel for activation
    private fun activateLookAndFeel(bundle: Bundle) {
        toolbar.setTitle(R.string.activate_title)
        actionButton.text = resources.getText(R.string.activate_button)
        val success = bundle.getBoolean("success", false)
        val isExpired = bundle.getBoolean("isExpired", true)
        val isActivated = bundle.getBoolean("isActivated", false)
        val permission = bundle.getBoolean("permission", false)

        if (success) {
            if (!isExpired) {
                if (!isActivated) {
                    if (permission) {
                        GenerateQR().execute(bundle.getString("code", ""))
                        message1TextView.text = resources.getString(R.string.activation_confirmation_message)
                        message2TextView.text = bundle.getString("expiryDate", "error").substring(0, 10)
                    } else {
                        message1TextView.text = resources.getString(R.string.activation_fail_permission_message)
                        message2TextView.text = bundle.getString("outletName", "error")
                    }
                } else {
                    message1TextView.text = resources.getString(R.string.activation_fail_active_message)
                    message2TextView.text = ""
                }
            } else {
                message1TextView.text = resources.getString(R.string.voucher_expired_message)
                message2TextView.text = bundle.getString("expiryDate", "errorerrorerrorerror").substring(0, 10)
            }
        } else {
            message1TextView.text = resources.getString(R.string.voucher_not_found_message)
            message2TextView.text = ""
        }
        if (success && !isExpired && !isActivated && permission) {
            actionButton.text = resources.getString(R.string.activate_button)
            actionButton.setOnClickListener {
                BackendAPIRestClient(this.applicationContext).activate(bundle.getString("code"), object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                        super.onSuccess(statusCode, headers, response)
                        //dismiss dialog on success request
                        dialog?.dismiss()

                        var activateSuccess = false
                        try {
                            activateSuccess = response.getBoolean("success")
                        } catch (e: Exception) {
                        }
                        if (activateSuccess) {
                            setResult(RESULT_OK)
                            finish()
                        } else
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                    override fun onStart() {
                        super.onStart()
                        dialog?.show()

                    }

                })
            }
        } else {
            actionImageView.setImageResource(R.drawable.x)
            actionButton.text = resources.getString(R.string.back_button)
            actionButton.setOnClickListener {
                finish()
            }
        }

    }

    //handle look and feel for redeem
    private fun redeemLookAndFeel(bundle: Bundle) {
        toolbar.setTitle(R.string.redeem_title)
        actionButton.text = resources.getText(R.string.redeem_button)
        val success = bundle.getBoolean("success", false)
        val isExpired = bundle.getBoolean("isExpired", true)
        val isActivated = bundle.getBoolean("isActivated", false)
        val isRedeemed = bundle.getBoolean("isRedeemed", false)
        if (success) {
            if (!isExpired) {
                if (isActivated) {
                    if (!isRedeemed) {
                        message1TextView.text = resources.getString(R.string.redeem_confirmation_message)
                        message2TextView.text = bundle.getString("outletPromo", "error")
                    } else {
                        message1TextView.text = resources.getString(R.string.redeem_fail_redeemed_message)
                        message2TextView.text = bundle.getString("usedDate", "errorerrorerrorerror").substring(0, 10)
                    }
                } else {
                    message1TextView.text = resources.getString(R.string.redeem_fail_not_activated_message)
                    message2TextView.text = resources.getStringArray(R.array.outlet_name)[bundle.getInt("outletOrigin") - 1]
                }
            } else {
                message1TextView.text = resources.getString(R.string.voucher_expired_message)
                message2TextView.text = bundle.getString("expiryDate", "errorerrorerror").substring(0, 10)
            }
        } else {
            message1TextView.text = resources.getString(R.string.voucher_not_found_message)
            message2TextView.text = ""
        }
        if (success && !isExpired && isActivated && !isRedeemed) {
            actionImageView.setImageDrawable(resources.obtainTypedArray(R.array.outlet_image).getDrawable(bundle.getInt("outletId")-1))
            actionButton.text = resources.getString(R.string.redeem_button)
            actionButton.setOnClickListener {
                BackendAPIRestClient(this.applicationContext).redeem(bundle.getString("code"), bundle.getInt("outletId"), object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                        super.onSuccess(statusCode, headers, response)

                        dialog?.dismiss()
                        var redeemSuccess = false
                        try {
                            redeemSuccess = response.getBoolean("success")
                        } catch (e: Exception) {
                        }
                        if (redeemSuccess) {
                            setResult(RESULT_OK)
                            finish()
                        } else
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                    override fun onStart() {
                        super.onStart()
                        dialog?.show()

                    }

                })
            }
        } else {
            actionImageView.setImageResource(R.drawable.x)
            actionButton.text = resources.getString(R.string.back_button)
            actionButton.setOnClickListener {
                finish()
            }
        }
    }
}
