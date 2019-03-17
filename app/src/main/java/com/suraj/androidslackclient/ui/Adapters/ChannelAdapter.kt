package com.suraj.androidslackclient.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suraj.androidslackclient.Model.Channel
import com.suraj.androidslackclient.R
import com.suraj.androidslackclient.ui.ViewModel.MessageViewModel
import kotlinx.android.synthetic.main.channel_listitem.view.*

class ChannelAdapter(val context: Context, channlelist: ArrayList<Channel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var launchChannel: (positon: Int) -> Unit = {}



    var channel_list = channlelist as ArrayList<Channel>
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ChannelViewHolder(
            LayoutInflater.from(parent!!.context).inflate(
                R.layout.channel_listitem,
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var channel = channel_list.get(position)

        if (!channel.id.isNullOrEmpty()) {

            (holder as ChannelViewHolder).bind(channel, position)
            holder.itemView.bt_launch.setOnClickListener {


                launchChannel.invoke(position)
            }


        }


    }


    private var selectedPosition: Int? = null


    override fun getItemCount(): Int {
        return channel_list.size
    }

    fun addList(channellist: ArrayList<Channel>) {
        this.channel_list.clear()
        this.channel_list = channellist
        notifyDataSetChanged()


    }

}



class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(channel: Channel, position: Int) {
        itemView.tv_channelname.text = channel.name
        itemView.tv_membercount.text = "${channel.members.size} Members"


    }

}