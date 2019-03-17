package com.suraj.androidslackclient.Networking

import com.google.gson.JsonElement
import com.suraj.androidslackclient.Model.AccessToken
import com.suraj.androidslackclient.Model.MessageResponse
import com.suraj.androidslackclient.Model.SlackRtmReponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by suraj on 23/5/18.
 */

interface APISERVICE {


    @GET("api/rtm.connect")
    fun connectToSlackRtm(@Query("token") token: String): Call<SlackRtmReponse>


    /**
     * @return The access token of user for performing slack API's
     * @param CLIENT_ID
     * @param CLIENT_SECRET
     * @param CODE
     * @param REIRECT_URI
     */
    @FormUrlEncoded
    @PUT("api/oauth.access")
    fun exchangeVerificationToken(@FieldMap param:HashMap<String,String>): Call<AccessToken>


    /**
     * function for getting list of chat based on channel id
     * @param token
     * @param channel
     *
     *
     */


    @GET("api/channels.history")
    fun getChatHistoryFromChannelId(@Query("token") token: String,@Query("channel") channelID:String,@Query("count") count:String,@Query("inclusive")inclusive:String,@Query("oldest")oldest:String,@Query("latest")now:String): Call<MessageResponse>


}