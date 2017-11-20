package io.github.as3mbus.QRManager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.beust.klaxon.Parser
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val PREFS_NAME = "OutletPrefs"
    private var context: Context? = null
    private var redeemActivate = false
    var scanResultVar: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this.applicationContext

        val parser = Parser()

//        callButton.setOnClickListener {

        //            BackendAPIRestClient.getActive("abc123", object : JsonHttpResponseHandler() {
//                override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
//                    // If the response is JSONObject instead of expected JSONArray
//
//                    val json: JsonObject = parser.parse(response.toString()) as JsonObject
//                    val message = json.string("message")
//
//                    // Do something with the response
//                    Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
//                }
//            })


//            val client = AsyncHttpClient()
//            client.get("http://api.img4me.com/?text=Testing", object : TextHttpResponseHandler() {
//                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
//                    Toast.makeText(context, "connection failure", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
//                    val i = Intent(context, RedeemActivity::class.java)
//
//                    //Create the bundle
//                    val bundle = Bundle()
//
//                    //Add your data to bundle
//                    bundle.putString("outlet", responseString)
//
//                    //Add the bundle to the intent
//                    i.putExtras(bundle)
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    //Fire that second activity
//
//
//                    ContextCompat.startActivity(context, i, bundle)
//                }
//            })


//            BackendAPIRestClient.get("?text=Testing",null, object : TextHttpResponseHandler{
//                override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
//                    // If the response is JSONObject instead of expected JSONArray
//
//                    val json: JsonObject = parser.parse(response.toString()) as JsonObject
//                    val message = json.string("message")
//
//                    // Do something with the response
//                    Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
//                }
//            })
//            val i = Intent(this, RedeemActivity::class.java);
//
//            //Create the bundle
//            val bundle = Bundle();
//
//            //Add your data to bundle
//            bundle.putString("outlet", "eyooo");
//
//            //Add the bundle to the intent
//            i.putExtras(bundle);
//
//            //Fire that second activity
//
//            ContextCompat.startActivity(this, i, bundle);

//        }
        scanButton.setOnClickListener {

            val intentIntegr = IntentIntegrator(this)
            intentIntegr.initiateScan()
            redeemActivate = true
        }
        reportButton.setOnClickListener {
            val intentIntegr = IntentIntegrator(this)
            intentIntegr.initiateScan()
            redeemActivate = false
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val scanResult: IntentResult?
        scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        if (scanResult != null) {
            scanResultVar = scanResult.contents
            if (scanResultVar != null) {
                Toast.makeText(this.applicationContext, "contacting server", Toast.LENGTH_SHORT).show()
                val settings = getSharedPreferences(PREFS_NAME, 0)
                val outletid = settings.getInt("outletid", -1)
                if (redeemActivate) {
                    BackendAPIRestClient(this.applicationContext).getIsRedeemed(scanResult.contents,outletid, object : JsonHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                            super.onSuccess(statusCode, headers, response)

                            val i = Intent(context, RedeemActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                            var success = false
                            var isExpired = true
                            var isActivated = false
                            var isRedeemed = true
                            var outletOrigin = -1
                            var expiryDate = ""
                            var outletOriginName = ""
                            var outletPromo = ""
                            var usedDate = ""

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
                            bundle.putBoolean("redeemActivate", redeemActivate)
                            bundle.putBoolean("success", success)
                            bundle.putBoolean("isExpired", isExpired)
                            bundle.putBoolean("isActivated", isActivated)
                            bundle.putBoolean("isRedeemed", isRedeemed)
                            bundle.putInt("outletOrigin",outletOrigin);
                            bundle.putString("expiryDate", expiryDate)
                            bundle.putString("outletOriginName", outletOriginName)
                            bundle.putString("outletPromo", outletPromo)
                            bundle.putString("usedDate", usedDate)




                            //Add the bundle to the intent
                            i.putExtras(bundle)
                            //Fire that second activity
                            startActivity(i, bundle)
//                        Toast.makeText(context, response?.getString("status"), Toast.LENGTH_SHORT).show()

                        }
                    })
                } else {
                    BackendAPIRestClient(this.applicationContext).getIsActive(scanResult.contents, object : JsonHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                            super.onSuccess(statusCode, headers, response)

                            val i = Intent(context, RedeemActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            var success = false
                            var isExpired = true
                            var isActivated = false
                            var outletOrigin = -1
                            var expiryDate = ""
                            var outletName = ""

                            try {
                                success = response.getBoolean("success")
                                isExpired = response.getBoolean("isExpired")
                                isActivated = response.getBoolean("isActivated")
                                outletOrigin = response.getJSONObject("vochercode")?.getInt("outletOrigin")!!
                                expiryDate = response.getJSONObject("vochercode")?.getString("expiryDate")!!
                                outletName = response.getJSONObject("vochercode")?.getJSONObject("Outlet")?.getString("name")!!
                            } catch (e: Exception) {
                            }
                            val permission = outletOrigin == outletid

                            //Create the bundle
                            val bundle = Bundle()

                            //Add your data to bundle
                            bundle.putString("code", scanResultVar)
                            bundle.putBoolean("redeemActivate", redeemActivate)
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
//                        Toast.makeText(context, response?.getString("status"), Toast.LENGTH_SHORT).show()

                        }
                    })
                }


            }

        } else
            Toast.makeText(this, "scan Canceled", Toast.LENGTH_SHORT).show()
    }
}
