package io.github.as3mbus.QRManager

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.string
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject


/**
 * Created by as3mbus on 10/11/17.
 */
class BackendAPI {

    val parser: Parser = Parser()
    @Throws(JSONException::class)
    fun isVoucherActive(result:String) {
        BackendAPIRestClient.get(
                "statuses/public_timeline.json",
                null, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
                // If the response is JSONObject instead of expected JSONArray

                val json: JsonObject = parser.parse(response.toString()) as JsonObject
                val tweetText = json.string("text")

                // Do something with the response
                println(tweetText)
            }
        })
    }
}