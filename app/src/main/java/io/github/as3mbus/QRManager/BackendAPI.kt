package io.github.as3mbus.QRManager

import org.json.JSONObject
import org.json.JSONArray
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
//import com.beust.klaxon.string



/**
 * Created by as3mbus on 10/11/17.
 */
public class BackendAPI{

    @Throws(JSONException::class)
    fun getPublicTimeline() {
        BackendAPIRestClient.get("statuses/public_timeline.json", null, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
                // If the response is JSONObject instead of expected JSONArray
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, timeline: JSONArray) {
                // Pull out the first event on the public timeline
                val firstEvent = timeline.get(0)
//                val tweetText = firstEvent.("text")

                // Do something with the response
//                println(tweetText)
            }
        })
    }
}