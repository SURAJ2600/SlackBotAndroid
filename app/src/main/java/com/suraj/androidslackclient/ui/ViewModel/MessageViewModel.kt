package com.suraj.androidslackclient.ui.ViewModel

import allbegray.slack.SlackClientFactory
import allbegray.slack.rtm.*
import allbegray.slack.rtm.EventListener
import allbegray.slack.type.User
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.JsonNode
import com.google.gson.Gson
import com.google.gson.JsonElement

import com.suraj.androidslackclient.Networking.ApiClient

import com.suraj.androidslackclient.Utilities.LogsUtils
import com.suraj.androidslackclient.Utilities.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.suraj.androidslackclient.Model.AccessToken
import com.suraj.androidslackclient.Model.Channel
import com.suraj.androidslackclient.Model.Message
import com.suraj.androidslackclient.Model.MessageResponse
import com.suraj.androidslackclient.Utilities.PreferenceUtils
import java.util.*

import java.util.concurrent.Executors
import kotlin.collections.ArrayList


/**
 * @author SURAJ created on 16/03/2019
 *
 *
 */
enum class MessageViewState {
    LOADING, ERROR, SUCCESS, NONE
}

class MessageViewModel(var context: Application) : AndroidViewModel(context) {
    private val TAG = MessageViewModel::class.java.simpleName


    val apiServiceClient = ApiClient.create()
    lateinit var mRtmClient: SlackRealTimeMessagingClient;
    val errorMessage = MutableLiveData<String>()
    var mWebApiClient = SlackClientFactory.createWebApiClient("")
    var WEBSOCKET_URL = ""
    var channel_ID = ""
    var channel_name = ""
    val successMessage = MutableLiveData<String>()


    var mMessageList = ArrayList<Message>()
    var mMessageLivedata = MutableLiveData<ArrayList<Message>>()
    internal val state = MutableLiveData<ChatListState>()
    var executer_service = Executors.newSingleThreadExecutor()


    init {

        state.value = ChatListState.NONE
        mWebApiClient = SlackClientFactory.createWebApiClient("${Util.bot_accesstokne}")
        executer_service.submit {
            WEBSOCKET_URL = mWebApiClient.startRealTimeMessagingApi().findPath("url").asText();
            mRtmClient = SlackRealTimeMessagingClient(WEBSOCKET_URL)
            initRtmClient()
        }


    }


    /**
     *
     *
     *
     * @return Ths list of messages from channel ,
     * @param Channel_ID
     * @param inculsive
     * @param oldest
     * @param now
     */

    fun getMessageFromChannleId() {
        state.postValue(ChatListState.LOADING)
        if (Util.isConnected(context)) {

            var call = apiServiceClient.getChatHistoryFromChannelId(
                PreferenceUtils.getInstance(context).getusertoken(),
                channel_ID,
                Util.default_count
                , "1"
                , "1"
                , "" + System.currentTimeMillis()
            )


            call.enqueue(object : Callback<MessageResponse> {
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    errorMessage.postValue("Server error please try later")

                }

                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {

                    if (response.isSuccessful) {
                        LogsUtils.makeLogE("valuess", ">>>" + response.body().toString())
                        if (response.body()?.ok as Boolean) {


                            mMessageList.addAll(response.body()?.messages as ArrayList<Message>)

                            try {


                                Collections.reverse(mMessageList)
                                mMessageLivedata.postValue(mMessageList)
                                successMessage.postValue("Data loaded successfully")


                                state.postValue(ChatListState.SUCCESS)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                mMessageLivedata.postValue(mMessageList)
                                successMessage.postValue("Data loaded successfully")

                                state.postValue(ChatListState.ERROR)
                            }


                        }
                    }
                }
            })

        } else {
            errorMessage.postValue("Please enable internet")
            state.value = ChatListState.ERROR

        }


    }

    /**
     *
     * Init the  RTM client for communicating with slack on realtime
     */
    fun initRtmClient() {

        mRtmClient.addListener(Event.CHANNEL_HISTORY_CHANGED, object : allbegray.slack.rtm.EventListener {
            override fun onMessage(p0: JsonNode?) {
                getMessageFromChannleId()
            }


        })

        mRtmClient.addListener(Event.MESSAGE, object : allbegray.slack.rtm.EventListener {
            override fun onMessage(p0: JsonNode?) {

                context.run {
                    getMessageFromChannleId()
                }
            }

        })
        mRtmClient.addCloseListener(object : CloseListener {
            override fun onClose() {


                LogsUtils.makeLogD(TAG, "RTM CLOSED")
            }


        });

        mRtmClient.addFailureListener(object : FailureListener {
            override fun onFailure(p0: Throwable?) {
                LogsUtils.makeLogE(TAG, "RTM FAILURE")

            }

        });



        mRtmClient.connect()

    }


    /*
    *
    * Method for sending relatime message to channle
    * */

    fun sendMessage(message: String) {


        state.value = ChatListState.LOADING
        executer_service.submit {
            mWebApiClient.meMessage(channel_ID, "$message")
            successMessage.postValue("Message sent successfully")
            state.value = ChatListState.SUCCESS

        }

    }


    fun getUserNameFromId(userid: String) {
        executer_service.submit { mWebApiClient.getUserInfo(userid) }
    }


}

