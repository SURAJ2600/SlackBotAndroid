package com.suraj.androidslackclient.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.suraj.androidslackclient.R
import com.suraj.androidslackclient.Utilities.PreferenceUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        /*
        * Remove status bar from top of the screen
        *
        * */


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );


        getHandler();

    }

    private fun getHandler() {

        Handler().postDelayed({


            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()


        }, 3000)


    }
}
