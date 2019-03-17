package com.suraj.androidslackclient.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suraj.androidslackclient.Model.Channel
import com.suraj.androidslackclient.Model.Message
import com.suraj.androidslackclient.R
import kotlinx.android.synthetic.main.channel_listitem.view.*
import kotlinx.android.synthetic.main.meesagelist_item.view.*

class MessageAdapter(val context: Context, messagelist: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var launchChannel: (positon: Int) -> Unit = {}


    var message_list = messagelist as ArrayList<Message>
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MessageViewHolder(
            LayoutInflater.from(parent!!.context).inflate(
                R.layout.meesagelist_item,
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        var message=message_list.get(position)


        if(!message.user.isNullOrEmpty())
        {


            (holder as MessageViewHolder).bind(message)
        }




    }


    private var selectedPosition: Int? = null


    override fun getItemCount(): Int {
        return message_list.size
    }

    fun addList(messagelist: ArrayList<Message>) {
        this.message_list.clear()
        this.message_list = messagelist
        notifyDataSetChanged()


    }


}

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message:Message)
    {


        itemView.tv_username.text=message.user
        itemView.tv_message.text=message.text

    }




}