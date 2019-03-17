package com.suraj.androidslackclient.Socket

import org.json.JSONObject

interface Callback<T> {

    fun onResponse(response: JSONObject)

    fun onError(e: Exception)

}