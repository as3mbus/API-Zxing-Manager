package io.github.as3mbus.QRManager

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.beust.klaxon.Parser
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    private var context: Context? = null
    private val REQUEST_ENABLE_BT = 1
    private val DISCOVER_DURATION = 300
    private val REQUEST_BLU = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this.applicationContext

        val parser = Parser()

        callButton.setOnClickListener {

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
            val client = AsyncHttpClient()
            client.get("http://api.img4me.com/?text=Testing", object : TextHttpResponseHandler() {
                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    Toast.makeText(context, "connection failure", Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    val i = Intent(context, RedeemActivity::class.java)

                    //Create the bundle
                    val bundle = Bundle()

                    //Add your data to bundle
                    bundle.putString("outlet", responseString)

                    //Add the bundle to the intent
                    i.putExtras(bundle)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    //Fire that second activity


                    ContextCompat.startActivity(context, i, bundle)
                }
            })
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

        }
        scanButton.setOnClickListener {

            val intentIntegr = IntentIntegrator(this)
            intentIntegr.initiateScan()
        }
        reportButton.setOnClickListener {
            DataManager.writePhone(this, "data.txt", null, "hello world".toByteArray())
            DataManager.readPhone(this, "data.txt", null)
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "Device not support bluetooth", Toast.LENGTH_LONG).show()
                // Device does not support Bluetooth
            }
            if (!mBluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }

            val pairedDevices = mBluetoothAdapter.bondedDevices
            if (pairedDevices.size > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (device in pairedDevices) {
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    print(deviceName + "  " + deviceHardwareAddress)
                }
            } else
                print("no paired device")
            val discoveryIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION)
            startActivityForResult(discoveryIntent, REQUEST_BLU)

        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val scanResult: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        if (scanResult != null)
//            editText.setText(scanResult.contents)
            if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {
                val i = Intent()
                i.action = Intent.ACTION_SEND
                i.type = "*/*"

                val file = File(applicationContext.getExternalFilesDir(null).path, "data.txt")
//            editText.setText(file.path)
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))

                val pm = packageManager
                val list = pm.queryIntentActivities(i, 0)
                if (list.size > 0) {
                    var packageName: String? = null
                    var className: String? = null
                    var found = false

                    for (info in list) {
                        packageName = info.activityInfo.packageName
                        if (packageName == "com.android.bluetooth") {
                            className = info.activityInfo.name
                            found = true
                            break
                        }
                    }
                    //CHECK BLUETOOTH available or not------------------------------------------------
                    if (!found) {
                        Toast.makeText(this, "Bluetooth han't been found", Toast.LENGTH_LONG).show()
                    } else {
                        i.setClassName(packageName!!, className!!)
                        startActivity(i)
                    }
                }
            } else {
                Toast.makeText(this, "Bluetooth is cancelled", Toast.LENGTH_LONG).show()
            }

        // else continue with any other code you need in the method

    }
}
