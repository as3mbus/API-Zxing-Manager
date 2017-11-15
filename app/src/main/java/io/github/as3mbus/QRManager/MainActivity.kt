package io.github.as3mbus.QRManager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.beust.klaxon.Parser
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
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
            if (scanResultVar!=null){
                val i = Intent(this.applicationContext, RedeemActivity::class.java)

                //Create the bundle
                val bundle = Bundle()

                //Add your data to bundle
                bundle.putString("outlet", scanResultVar)
                bundle.putBoolean("redeemActivate", redeemActivate)

                //Add the bundle to the intent
                i.putExtras(bundle)
                //Fire that second activity
                startActivity(i, bundle)
            }

        } else
            Toast.makeText(this, "scan Canceled", Toast.LENGTH_SHORT).show();
    }
}
