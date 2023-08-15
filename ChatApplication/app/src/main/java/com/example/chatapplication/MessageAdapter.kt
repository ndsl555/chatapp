package com.example.chatapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageAdapter(val context: Context,val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1
    val ITEM_SEND=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }
        else{
            val view : View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]
        if(holder.javaClass==SendViewHolder::class.java){
            // do job for send view holder
            val viewHolder = holder as SendViewHolder
            holder.sendMessage.text=currentMessage.message
            val view = LayoutInflater.from(context).inflate(R.layout.delete_msg,null)
            val builder = AlertDialog.Builder(context)
                .setTitle("選項")
                .setView(view)
                .create()

            viewHolder.itemView.setOnLongClickListener {
                builder.show()
                val txt_everyone=view.findViewById<TextView>(R.id.everyone)
                val txt_cancel=view.findViewById<TextView>(R.id.cancel)
                //val btn_delself=view.findViewById<TextView>(R.id.delete_for_yourself)



                txt_everyone.setOnClickListener {
                    holder.sendMessage.text="此訊息被已收回"

                    builder.dismiss()
                }
                txt_cancel.setOnClickListener {
                    builder.dismiss()
                }


            false
            }
        }
        else{
            // do job for receive view holder
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message

        }


    }
    class SendViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val sendMessage = itemView.findViewById<TextView>(R.id.txt_send_msg)
    }
    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_msg)

    }
}