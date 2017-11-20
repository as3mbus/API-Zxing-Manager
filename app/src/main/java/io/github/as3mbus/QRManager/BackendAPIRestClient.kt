package io.github.as3mbus.QRManager

import android.content.Context
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams

/**
 * Created by as3mbus on 09/11/17.
 */
public class BackendAPIRestClient(context: Context) {
    private val BASE_URL = context.resources.getString(R.string.host)

    private val client = AsyncHttpClient()

    fun get(url: String, params: RequestParams?, responseHandler: AsyncHttpResponseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler)
    }

    fun getIsActive(id: String, responseHandler: AsyncHttpResponseHandler) {
        client.post(getAbsoluteUrl("vochercode/isactivated"), RequestParams("barcode", id), responseHandler)
    }

    fun getIsRedeemed(id: String, outletId: Int, responseHandler: AsyncHttpResponseHandler) {
        val reqParam = RequestParams()
        reqParam.put("barcode", id)
        reqParam.put("outletId", outletId)
        client.post(getAbsoluteUrl("outletcode/isredeemed"), reqParam, responseHandler)
    }

    fun activate(id: String, responseHandler: AsyncHttpResponseHandler) {
        client.post(getAbsoluteUrl("vochercode/activated"), RequestParams("barcode", id), responseHandler)
    }

    fun redeem(id: String, outletId: Int, responseHandler: AsyncHttpResponseHandler) {
        val reqParam = RequestParams()
        reqParam.put("barcode", id)
        reqParam.put("outletId", outletId)
        client.post(getAbsoluteUrl("outletcode/create"), reqParam, responseHandler)
    }
    fun outletStatus(outletId: Int, responseHandler: AsyncHttpResponseHandler) {
        client.post(getAbsoluteUrl("outlet/detail"), RequestParams("outletId", outletId), responseHandler)
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