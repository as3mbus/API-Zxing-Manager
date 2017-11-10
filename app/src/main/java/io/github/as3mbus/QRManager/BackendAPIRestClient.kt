package io.github.as3mbus.QRManager

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams

/**
 * Created by as3mbus on 09/11/17.
 */
public class BackendAPIRestClient{
    companion object {
        private val BASE_URL = "http://103.246.107.62:23000/"

        private val client = AsyncHttpClient()

        fun get(url: String, params: RequestParams?, responseHandler: AsyncHttpResponseHandler) {
            client.get(getAbsoluteUrl(url), params, responseHandler)
        }
        fun getActive(id: String, responseHandler: AsyncHttpResponseHandler) {
            client.get(getAbsoluteUrl("active/"+id), null, responseHandler)
        }

        fun post(url: String, params: RequestParams?, responseHandler: AsyncHttpResponseHandler) {
            client.post(getAbsoluteUrl(url), params, responseHandler)
        }
        fun patch(url: String, params: RequestParams?, responseHandler: AsyncHttpResponseHandler) {
            client.patch(getAbsoluteUrl(url), params, responseHandler)
        }

        fun getAbsoluteUrl(relativeUrl: String): String {
            return BASE_URL + relativeUrl
        }
    }

}