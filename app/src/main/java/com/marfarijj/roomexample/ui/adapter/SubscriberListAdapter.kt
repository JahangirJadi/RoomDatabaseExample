package com.marfarijj.roomexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.marfarijj.roomexample.R
import com.marfarijj.roomexample.data.database.Subscriber
import com.marfarijj.roomexample.databinding.ItemSubscriberBinding

class SubscriberListAdapter(
    private val subscriberList: List<Subscriber>,
    private val subscriberClickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<SubscriberListAdapter.MyViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(pos:Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSubscriberBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_subscriber, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
return subscriberList.size    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subscriber = subscriberList[position]
        holder.bind(subscriber, subscriberClickListener)

    }


    class MyViewHolder(private val binding: ItemSubscriberBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(subscriber: Subscriber,clickListener: (Subscriber) -> Unit) {
            binding.tvEmail.text = subscriber.email
            binding.tvName.text = subscriber.name
            binding.layoutItemSubscriber.setOnClickListener{
                clickListener(subscriber)
            }

        }


    }

}