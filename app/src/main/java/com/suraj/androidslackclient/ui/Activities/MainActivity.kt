package com.suraj.androidslackclient.ui.Activities

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.suraj.androidslackclient.Extensioin.obtainViewModel
import com.suraj.androidslackclient.R
import com.suraj.androidslackclient.Utilities.LogsUtils
import com.suraj.androidslackclient.Utilities.Util
import com.suraj.androidslackclient.ui.Adapters.ChannelAdapter
import com.suraj.androidslackclient.ui.ViewModel.ChatListViewModel

import com.suraj.androidslackclient.ui.ViewModel.ChatListState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ChatListViewModel

    var code = ""
    lateinit var mprogress_Dailog: Dialog
    private var status: Boolean = false
    private var channlelist = ArrayList<com.suraj.androidslackclient.Model.Channel>()
    lateinit var channel_adapter: ChannelAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = obtainViewModels()
        mprogress_Dailog = Util.ShowProgressView(this)

        initViews()
        setChannelAdapter()


        /*
         * Checking the whether access token of user is present in shared preference or else authenticate user to get access tokr
          * */

        status = viewModel.checkAccessToken()
        if (!status) {
            initial_view.visibility = View.VISIBLE
            channel_list.visibility = View.GONE
        } else {
            initial_view.visibility = View.GONE
            channel_list.visibility = View.VISIBLE

        }
    }


    /**
     *
     * @ Initialize the views from layout,Android trigger actions for click events
     */
    fun initViews() {

        tv_title.text = "Home"
        img_back.setOnClickListener {
            finish()
        }

        bt_authenticate.setOnClickListener { authenticateUser(); }


    }
    /*
    *
    * Set the adapter,And wire that inot the channel list recycler view
    * */

    fun setChannelAdapter() {

        channel_adapter = ChannelAdapter(this, channlelist)
        channel_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            adapter = channel_adapter
        }

        /*
        *
        * Click event triggered from recycler view adapter
        *
        * */

        channel_adapter.launchChannel = {

            var data = Bundle()
            data.putString("channel_id", "" + channlelist.get(it).id)
            data.putString("channel_name", "" + channlelist.get(it).name)

            val intent = Intent(this, MessageActivity::class.java)
            intent.putExtras(data)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        if (!status) {

            if (intent.data != null) {
                var uri = intent.data as Uri
                if (uri != null && uri.toString().startsWith(Util.redirectURi)) {
                    code = uri.getQueryParameter("code")
                    LogsUtils.makeLogE("codes", ">>>" + code)
                    viewModel.getAccessToken(code)
                }
            }
        } else {

            channel_list.visibility = View.VISIBLE
            initial_view.visibility = View.GONE
            if (viewModel.mChannellist.size == 0) {
                viewModel.getachanellist()
            }
        }



        viewModel.apply {

            viewModel.state.observe(this@MainActivity, Observer {

                when (it) {
                    ChatListState.SUCCESS -> {
                        mprogress_Dailog.dismiss()
                        initial_view.visibility = View.GONE
                        channel_list.visibility = View.VISIBLE
                        LogsUtils.snackBarAction(main_view, successMessage.value)


                    }
                    ChatListState.ERROR -> {
                        mprogress_Dailog.dismiss()
                        //LogsUtils.snackBarAction(main_view,errorMessage.value)
                    }
                    ChatListState.LOADING -> {
                        mprogress_Dailog.show()
                    }
                    ChatListState.NONE -> {
                        mprogress_Dailog.dismiss()


                    }


                }


            })

            viewModel.channel_livedate.observe(this@MainActivity, Observer {

                if (it.size != 0) {
                    channlelist = it
                    setChannelAdapter()
                }
                channel_list.adapter = channel_adapter

            })


        }

    }

    /**
     *
     * @return the code for excahnging the user from slack API
     */


    fun authenticateUser() {

        if (intent.data == null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("" + Util.getSlackAuthorizeUrl()))
            startActivity(intent)
        }
    }


    /*
    *
    * Obtain the viewmodel from viewmodel factory
    *
    * */


    fun obtainViewModels(): ChatListViewModel = obtainViewModel(ChatListViewModel::class.java)
}
