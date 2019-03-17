package com.suraj.androidslackclient.Utilities

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtils(context: Context) {

    private val key_accesstoken = "user_id"
    private val key_teamname = "user_type"
    private val key_teamid = "user_role"
    private val key_userid = "user_loggedin"
    private val key_approve_status = "approve_status"
    lateinit var sharedpreferences: SharedPreferences

    init {
        sharedpreferences = context.getSharedPreferences("androidslackclient", Context.MODE_PRIVATE)


    }

    /**
     * Clear share preferences.
     *
     * @param context the context
     */
    fun clearSharePreferences(context: Context) {

        val settings = context.getSharedPreferences("androidslackclient", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }

    /**
     * getuserid
     *
     * @return string
     */
    fun getuserid(): String {
        return sharedpreferences.getString(key_userid, "")
    }

    /**
     * Sets job seeker.
     *
     * @param userid the job seeker
     */
    fun setuserid(userid: String) {
        val editor = sharedpreferences.edit()
        editor.putString("" + key_userid, userid)
        editor.commit()
    }

    fun getusertoken(): String {
        return sharedpreferences.getString(key_accesstoken, "")
    }

    /**
     * Sets job seeker.
     *
     * @param usertoken the job seeker
     */
    fun setusertoken(usertoken: String) {
        val editor = sharedpreferences.edit()
        editor.putString("" + key_accesstoken, usertoken)
        editor.commit()
    }

    companion object {


        private val TAG = PreferenceUtils::class.java.simpleName
        /**
         * The constant myObj.
         */
        var myObj: PreferenceUtils? = null

        /**
         * The constant sharedpreferences.
         */

        /**
         * Create a static method to get instance.
         *
         * @param context the context
         * @return the instance
         */
        fun getInstance(context: Context): PreferenceUtils {
            if (myObj == null) {
                myObj = PreferenceUtils(context)
            }
            return myObj!!
        }
    }


}

