@file:Suppress("DEPRECATION")

package com.example.androidtechnicaltest.service.base

import com.example.androidtechnicaltest.repository.core.RemoteResponse
import com.example.androidtechnicaltest.util.EMPTY_STRING
import com.example.androidtechnicaltest.util.RESPONSE_ERROR
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import java.io.IOException

suspend fun baseGetRequest(requestURL: String): RemoteResponse {
    val mRequest = HttpGet(requestURL)
    val client = DefaultHttpClient()
    var code: Int
    var exception: String
    var body: String
    try {
        val response = client.execute(mRequest)
        code = response.statusLine.statusCode
        exception =  response.statusLine.reasonPhrase
        val entity = response.entity
        body = EntityUtils.toString(entity, "UTF-8")
    } catch (e: IOException) {
        mRequest.abort()
        code = 4
        exception = RESPONSE_ERROR
        body = EMPTY_STRING
    }
    return RemoteResponse(code, body, exception)
}

suspend fun baseGetWithHeadersRequest(requestURL: String, headers: List<Pair<String,String>>): RemoteResponse {
    val mRequest = HttpGet(requestURL)
    val client = DefaultHttpClient()
    headers.forEach {
        mRequest.addHeader(it.first, it.second)
    }
    var code: Int
    var exception: String
    var body: String
    try {
        val response = client.execute(mRequest)
        code = response.statusLine.statusCode
        exception =  response.statusLine.reasonPhrase
        val entity = response.entity
        body = EntityUtils.toString(entity, "UTF-8")
    } catch (e: IOException) {
        mRequest.abort()
        code = 4
        exception = RESPONSE_ERROR
        body = EMPTY_STRING
    }
    return RemoteResponse(code, body, exception)
}
