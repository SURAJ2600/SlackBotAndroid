package com.suraj.androidslackclient.ui.ViewModel

import allbegray.slack.SlackClientFactory
import allbegray.slack.rtm.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.suraj.androidslackclient.Networking.ApiClient

import com.suraj.androidslackclient.Utilities.LogsUtils
import com.suraj.androidslackclient.Utilities.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.suraj.androidslackclient.Model.AccessToken
import com.suraj.androidslackclient.Model.Channel
import com.suraj.androidslackclient.R
import com.suraj.androidslackclient.Utilities.PreferenceUtils
import java.util.concurrent.Executors


/**
 * @author SURAJ created on 16/03/2019
 *
 *
 */
enum class ChatListState {
    LOADING, ERROR, SUCCESS, NONE, SHOWINITIALVIEW, SHOWUSERVIEW
}

class ChatListViewModel(var context: Application) : AndroidViewModel(context) {


    val apiServiceClient = ApiClient.create()
    lateinit var mRtmClient: SlackRealTimeMessagingClient;
    val errorMessage = MutableLiveData<String>()
    var mWebApiClient = SlackClientFactory.createWebApiClient("")

    var mChannellist = ArrayList<Channel>()
    var channel_livedate = MutableLiveData<ArrayList<Channel>>()


    var mChannelId = ""


    var WEBSOCKET_URL = ""
    val successMessage = MutableLiveData<String>()
    internal val state = MutableLiveData<ChatListState>()
    var executer_service = Executors.newSingleThreadExecutor()


    init {

        mWebApiClient = SlackClientFactory.createWebApiClient("${Util.bot_accesstokne}")

        executer_service.submit {
            WEBSOCKET_URL = mWebApiClient.startRealTimeMessagingApi().findPath("url").asText();

        }

    }


    /*
    * 
    * @method checks the whether access token present in user shared preference 
    * 
    * */


    fun checkAccessToken(): Boolean {
        return if (!PreferenceUtils.getInstance(context).getusertoken().toString().equals("")) {
            return true
        } else {
            return false
        }

    }


    fun initSocket() {

        try {
            state.value = ChatListState.LOADING
            executer_service.submit {
                var channel_list = mWebApiClient.channelList as ArrayList<allbegray.slack.type.Channel>

                if (channel_list.size != 0) {
                    mChannellist.clear()
                    channel_livedate.postValue(mChannellist)
                    for (i in 0..channel_list.size - 1) {
                        try {
                            var channel = Channel(
                                channel_list.get(i).id,
                                channel_list.get(i).name,
                                channel_list.get(i).is_channel.toBoolean(),
                                channel_list.get(i).members,
                                channel_list.get(i).topic
                            )
                            mChannellist.add(channel)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }



                    if (mChannellist.size != 0) {
                        channel_livedate.postValue(mChannellist)
                        successMessage.postValue(context.getString(R.string.loaded_success))

                        state.postValue(ChatListState.SUCCESS)


                    } else {
                        errorMessage.postValue(context.getString(R.string.server_error))

                        state.postValue(ChatListState.ERROR)
                    }
                    
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setStateNone() {
        state.postValue(ChatListState.NONE)
    }

    fun getAccessToken(code: String) {
        if (Util.isConnected(context)) {
            state.value = ChatListState.LOADING
            var params = HashMap<String, String>()
            params.put("client_id", Util.slack_clientID)
            params.put("client_secret", Util.slack_clientSecretKey)
            params.put("code", "$code")
            params.put("redirect_uri ", Util.redirectURi)

            var call = apiServiceClient.exchangeVerificationToken(params)
            call.enqueue(object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    

                    errorMessage.postValue(context.getString(R.string.server_error))
                    state.value = ChatListState.ERROR

                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {

                    LogsUtils.makeLogE("reponcse", "" + response.body().toString())
                    if (response.isSuccessful) {
                        PreferenceUtils.getInstance(context).setusertoken("${response.body()?.access_token}")
                        PreferenceUtils.getInstance(context).setuserid("${response.body()?.user_id}")
                        LogsUtils.makeLogE(
                            "useraccesstoken",
                            "???" + PreferenceUtils.getInstance(context).getusertoken()

                        )
                        getachanellist()

                        state.value = ChatListState.SUCCESS

                    }
                }

            })

        } else {

            
            errorMessage.postValue(context.getString(R.string.connect_internet))
            state.value = ChatListState.ERROR
        }

    }


    fun getachanellist() {
        initSocket()

    }

}

