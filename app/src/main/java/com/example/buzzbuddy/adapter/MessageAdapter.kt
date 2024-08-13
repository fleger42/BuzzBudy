package com.example.buzzbuddy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.buzzbuddy.data.MessageDto
import com.example.buzzbuddy.R

class MessageAdapter(val context: Context, val messageList: ArrayList<MessageDto>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    val ITEM_RECEIVE = 1;
    val ITEM_SENT = 2;

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceivedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.txt_received_message)

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(currentMessage.receive)
            return ITEM_SENT
        else
            return ITEM_RECEIVE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == ITEM_RECEIVE){
            val view = LayoutInflater.from(context).inflate(R.layout.received_message, parent, false)
            return  ReceivedViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
            return  SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceivedViewHolder
            holder.receivedMessage.text = currentMessage.message
        }
    }
}