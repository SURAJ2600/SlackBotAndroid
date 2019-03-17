package com.suraj.androidslackclient.Socket

import android.content.Context
import io.socket.client.Socket
import org.json.JSONObject

class SocketObserver(val activity: Context, val mSocket: Socket, val _callBack: Callback<*>) {

    fun listen(event: String?) {

        try {
            mSocket.on(event) { args ->
                activity.run {
                    try {
                        _callBack.onResponse(args[0] as JSONObject)
                    } catch (e: Exception) {
                        _callBack.onError(e)
                    }
                }
            }
        } catch (e: Exception) {
            _callBack.onError(e)
        }


    }


}


