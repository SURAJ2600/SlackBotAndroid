package com.suraj.androidslackclient.Socket

import io.socket.client.IO
import io.socket.client.Socket

object SocketClient {

    /**
     * EVENT PARAMS
     */
    val EVENT_MESSAGE_FROMCHANNEL = "messsage"
    var mSocket: Socket? = null

    fun getSocketClient(url: String): Socket {
        if (mSocket == null) {
            try {
                mSocket = IO.socket("" + url.replace("wss","https"));
                mSocket?.connect();
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
        return mSocket!!


    }




}
