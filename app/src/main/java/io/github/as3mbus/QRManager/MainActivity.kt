package io.github.as3mbus.QRManager

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    val PREFS_NAME = "OutletPrefs"

    private var context: Context? = null

    private var activateRedeem = true

    private var outletId = -1

    var scanResultVar: String? = null

    var dialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        context = this.applicationContext
        dialog = Dialog(this)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_connecting);

        val settings = getSharedPreferences(PREFS_NAME, 0)
        outletId = settings.getInt("outletId", -1)
        toolbar.title = resources.getString(R.string.hello_outlet_message, resources.getStringArray(R.array.outlet_name)[outletId - 1])

        activateButton.setOnClickListener {
            val intentIntegr = IntentIntegrator(this)
            intentIntegr.initiateScan()
            activateRedeem = true
        }
        redeemButton.setOnClickListener {

            val intentIntegr = IntentIntegrator(this)
            intentIntegr.initiateScan()
            activateRedeem = false
        }
    }

    override fun onResume() {
        super.onResume()
        statusRequest(outletId)

    }

    //handle scan result
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val scanResult: IntentResult?
        scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        if (scanResult != null) {
            scanResultVar = scanResult.contents
            if (scanResultVar != null) {
                Toast.makeText(this.applicationContext, "contacting server", Toast.LENGTH_SHORT).show()
                if (activateRedeem)
                    activateRequest(scanResult.contents, outletId)
                else
                    redeemRequest(scanResult.contents, outletId)
            }
        } else
            Toast.makeText(this, "scan Canceled", Toast.LENGTH_SHORT).show()
    }


    //handle scan result to activate code
    private fun activateRequest(code: String, outletId: Int) {
        BackendAPIRestClient(this.applicationContext).getIsActive(code, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                super.onSuccess(statusCode, headers, response)

                val i = Intent(context, RedeemActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //set default value pre-parse
                var success = false
                var isExpired = true
                var isActivated = false
                var outletOrigin = -1
                var expiryDate = ""
                var outletName = ""

                //parse response
                try {
                    success = response.getBoolean("success")
                    isExpired = response.getBoolean("isExpired")
                    isActivated = response.getBoolean("isActivated")
                    outletOrigin = response.getJSONObject("vochercode")?.getInt("outletOrigin")!!
                    expiryDate = response.getJSONObject("vochercode")?.getString("expiryDate")!!
                    outletName = response.getJSONObject("vochercode")?.getJSONObject("Outlet")?.getString("name")!!
                } catch (e: Exception) {
                    println("==============RETRIEVAL FAILURE===================")
                    println("" + success + " " + isExpired + " " + isActivated + " ")
                }
                val permission = outletOrigin == outletId

                //Create the bundle
                val bundle = Bundle()

                //Add your data to bundle
                bundle.putString("code", scanResultVar)
                bundle.putBoolean("activateRedeem", activateRedeem)
                bundle.putBoolean("success", success)
                bundle.putBoolean("isExpired", isExpired)
                bundle.putBoolean("isActivated", isActivated)
                bundle.putBoolean("permission", permission)
                bundle.putString("expiryDate", expiryDate)
                bundle.putString("outletName", outletName)


                //Add the bundle to the intent
                i.putExtras(bundle)
                //Fire that second activity
                startActivity(i, bundle)

                dialog?.dismiss()
            }

            override fun onStart() {
                super.onStart()
                dialog?.show()

            }

        })
    }

    //handle scan result for redeem request
    private fun redeemRequest(code: String, outletId: Int) {
        BackendAPIRestClient(this.applicationContext).getIsRedeemed(code, outletId, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                super.onSuccess(statusCode, headers, response)

                val i = Intent(context, RedeemActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //set default value pre-parse
                var success = false
                var isExpired = true
                var isActivated = false
                var isRedeemed = true
                var outletOrigin = -1
                var expiryDate = ""
                var outletPromo = ""
                var usedDate = ""

                //parse response
                try {
                    success = response.getBoolean("success")
                    isExpired = response.getBoolean("isExpired")
                    isRedeemed = response.getBoolean("isRedeemed")
                    outletPromo = response.getJSONObject("outletId")?.getString("promo")!!
                    isActivated = response.getJSONObject("vocher")?.getBoolean("activated")!!
                    outletOrigin = response.getJSONObject("vocher")?.getInt("outletOrigin")!!
                    expiryDate = response.getJSONObject("vocher")?.getString("expiryDate")!!
                    usedDate = response.getJSONObject("outletCode")?.getString("createdAt")!!
                } catch (e: Exception) {
                }

                //Create the bundle
                val bundle = Bundle()

                //Add your data to bundle
                bundle.putString("code", scanResultVar)
                bundle.putInt("outletId", outletId)
                bundle.putBoolean("activateRedeem", activateRedeem)
                bundle.putBoolean("success", success)
                bundle.putBoolean("isExpired", isExpired)
                bundle.putBoolean("isActivated", isActivated)
                bundle.putBoolean("isRedeemed", isRedeemed)
                bundle.putInt("outletOrigin", outletOrigin)
                bundle.putString("expiryDate", expiryDate)
                bundle.putString("outletPromo", outletPromo)
                bundle.putString("usedDate", usedDate)


                //Add the bundle to the intent
                i.putExtras(bundle)
                //Fire that second activity
                startActivity(i, bundle)

                dialog?.dismiss()
            }

            override fun onStart() {
                super.onStart()
                dialog?.show()

            }

        })
    }

    //retrive outlet status from API
    private fun statusRequest(outletId: Int) {
        BackendAPIRestClient(this.applicationContext).outletStatus(
                outletId,
                object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {

                        //set default value pre-parse
                        var originVoucherCount = 0
                        var activeOriginVoucherCount = 0
                        var activeVoucherCount = 0
                        var activeVoucherRedeemedCount = 0
                        //parse response
                        try {
                            activeVoucherCount = response?.getInt("vocherActiveCount")!!
                            activeVoucherRedeemedCount = response.getJSONObject("outlets")?.getInt("vocherRedeem")!!
                            originVoucherCount = response.getJSONObject("outlets")?.getInt("vocherOriginCount")!!
                            activeOriginVoucherCount = response.getJSONObject("outlets")?.getInt("vocherActiveOriginCount")!!
                        } catch (e: Exception) {
                        }
                        //set text view
                        activatedCount.text = "" + activeOriginVoucherCount + " / " + originVoucherCount
                        redeemedCount.text = "" + activeVoucherRedeemedCount + " / " + activeVoucherCount

                        dialog?.dismiss()
                    }

                    override fun onStart() {
                        super.onStart()
                        dialog?.show()

                    }

                }
        )
    }
}
