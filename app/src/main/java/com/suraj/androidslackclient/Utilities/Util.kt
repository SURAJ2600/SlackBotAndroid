package com.suraj.androidslackclient.Utilities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.view.LayoutInflater
import com.suraj.androidslackclient.R
import com.suraj.androidslackclient.ui.ViewModel.MessageViewModel

object Util {


    /**
     * @author SURAJ
     * This is the common class for defining  constant values
     */

    val slack_access_token = ""
    val slack_clientID = "362211397057.580405595494"
    val slack_clientSecretKey = "e2e05ac8ebcda85a0d8639f90f3beeb1"
    val redirectURi = "androidslackclient://callback"
    val scope = "channels:history"
    val BASE_URL = "https://slack.com/"
    val OAuth_URL = "oauth/authorize"
    val default_count = "100"

    val bot_accesstokne="xoxb-362211397057-578968705234-BXmf8SYar4Nn3k668SLjWAXq"




    fun isConnected(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


    fun getSlackAuthorizeUrl(): String {
        val builder = Uri.parse(BASE_URL+ OAuth_URL).buildUpon()
            .appendQueryParameter("client_id", "$slack_clientID")
            .appendQueryParameter("scope", "$scope")
            .appendQueryParameter("redirect_uri", "$redirectURi")

        return builder.build().toString()
    }


    fun ShowProgressView(mCtx: Context): Dialog {
        val factory = LayoutInflater.from(mCtx)

        val DialogView = factory.inflate(R.layout.progressview, null)

        val main_dialog = Dialog(mCtx)

        main_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        main_dialog.setCanceledOnTouchOutside(true)

        main_dialog.setCancelable(true)


        main_dialog.setContentView(DialogView)

        return main_dialog
    }

}
