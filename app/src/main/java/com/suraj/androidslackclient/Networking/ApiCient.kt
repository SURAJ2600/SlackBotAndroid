package com.suraj.androidslackclient.Networking


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit




/**
 * Created by Suraj on 23/5/18.
 */

class ApiClient private constructor() {


    companion object {
        fun getAPIurl(): String {


            return "https://slack.com/"

        }




        fun GetClient(): OkHttpClient {


            return OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
        }

        fun create(): APISERVICE {

            var retrofit = Retrofit.Builder()
                    .client(GetClient())
                    .baseUrl(getAPIurl())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build()


            return retrofit.create(APISERVICE::class.java)
        }

        fun getGson(): Gson {
            return GsonBuilder()
                    .setLenient()
                    .create();
        }
    }
}
