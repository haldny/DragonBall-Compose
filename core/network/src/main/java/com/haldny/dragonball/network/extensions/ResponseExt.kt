package com.haldny.dragonball.network.extensions

import com.haldny.dragonball.core.business.BusinessResult
import com.haldny.dragonball.core.business.exceptions.BusinessException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

fun <P, R> Response<P>.handleResponse(mapper: (P) -> R): BusinessResult<R> = if (isSuccessful) {
    body()?.let { response ->
        BusinessResult.Success(mapper(response))
    } ?: BusinessResult.Failure(BusinessException("Server returned a malformed response"))
} else {
    getErrorFromResponse()
}

fun <T> Response<T>.getErrorFromResponse(): BusinessResult.Failure {
    return errorBody()?.let {
        try {
            BusinessResult.Failure(BusinessException(JSONObject(it.string()).getString("message")))
        } catch (e: JSONException) {
            BusinessResult.Failure(BusinessException("Parsing server error response failed"))
        }
    } ?: BusinessResult.Failure(BusinessException("Server returned invalid error response."))
}
