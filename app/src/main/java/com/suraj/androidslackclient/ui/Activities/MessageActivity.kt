package com.suraj.androidslackclient.ui.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.suraj.androidslackclient.Extensioin.obtainViewModel
import com.suraj.androidslackclient.Model.Message
import com.suraj.androidslackclient.R
import com.suraj.androidslackclient.Utilities.LogsUtils
import com.suraj.androidslackclient.Utilities.Util
import com.suraj.androidslackclient.ui.Adapters.MessageAdapter
import com.suraj.androidslackclient.ui.ViewModel.ChatListState
import com.suraj.androidslackclient.ui.ViewModel.MessageViewModel
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import androidx.databinding.adapters.TextViewBindingAdapter.setText

import android.widget.EditText
import android.view.LayoutInflater

import android.app.AlertDialog
import kotlinx.android.synthetic.main.add_message.view.*


class MessageActivity : AppCompatActivity() {
    lateinit var viewmodel: MessageViewModel
    lateinit var mprogressDialog: Dialog

    lateinit var message_adapter: MessageAdapter
    var messagelist = ArrayList<Message>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        mprogressDialog = Util.ShowProgressView(this)

        viewmodel = obtainViewModels()
        initViews()
        setAdapterForMEssageList()


        viewmodel.apply {

            state.observe(this@MessageActivity, Observer {

                when (it) {

                    ChatListState.ERROR -> {
                        tv_nodata.visibility = View.VISIBLE
                        message_list.visibility = View.GONE
                        mprogressDialog.dismiss()
                    }
                    ChatListState.LOADING -> {
                        tv_nodata.visibility = View.GONE
                        message_list.visibility = View.GONE
                        mprogressDialog.show()

                    }
                    ChatListState.NONE -> {

                        tv_nodata.visibility = View.GONE
                        message_list.visibility = View.VISIBLE
                        mprogressDialog.dismiss()


                    }
                    ChatListState.SUCCESS -> {

                        tv_nodata.visibility = View.GONE
                        message_list.visibility = View.VISIBLE
                        mprogressDialog.dismiss()


                    }


                }


            })

            mMessageLivedata.observe(this@MessageActivity, Observer {


                if (it.size != 0) {

                    messagelist = it
                    setAdapterForMEssageList()
                }


            })


        }


    }


    fun setAdapterForMEssageList() {


        message_adapter = MessageAdapter(this, messagelist)

        message_list.apply {

            layoutManager = LinearLayoutManager(this@MessageActivity)
            adapter = message_adapter
        }

    }

    /**
     *
     * @ Initialize the views from layout,Android trigger actions for click events
     */
    fun initViews() {

        tv_title.text = "Messaging"
        img_back.setOnClickListener {
            finish()
        }
        if (intent != null) {
            var data = intent.extras
            viewmodel.channel_ID = data.getString("channel_id")
            viewmodel.channel_name = data.getString("channel_name")
            LogsUtils.makeLogE("channelsids", ">>>" + data.getString("channel_id"))
            viewmodel.getMessageFromChannleId()
        }


        add_message.setOnClickListener {
            sendMessageView()
            LogsUtils.snackBarAction(message_view, "Fab clicked")
        }


    }


    fun sendMessageView() {


        val dialogBuilder = AlertDialog.Builder(this)
// ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.add_message, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        var tv_tochannel = dialogView.tv_touser
        var bt_cancel = dialogView.bt_cancel
        var bt_send = dialogView.bt_send

        var chat_box = dialogView.editText


        tv_tochannel.text = "To : #${viewmodel.channel_name}"

        bt_cancel.setOnClickListener {
            alertDialog.dismiss()
        }


        bt_send.setOnClickListener {
            viewmodel.sendMessage("" + chat_box.text.toString())
            alertDialog.dismiss()
        }


    }


    fun obtainViewModels(): MessageViewModel = obtainViewModel(MessageViewModel::class.java)

}
