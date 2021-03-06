package com.marfarijj.roomexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marfarijj.roomexample.R
import com.marfarijj.roomexample.data.database.Subscriber
import com.marfarijj.roomexample.data.database.SubscriberDatabase
import com.marfarijj.roomexample.data.repository.SubscriberRepository
import com.marfarijj.roomexample.databinding.ActivityMainBinding
import com.marfarijj.roomexample.ui.adapter.SubscriberListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initView()

        viewModel.message.observe(this, Observer { it ->
            it.getContentIfNotHandled()?.let {
               Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
           }
        })
    }

    private fun listItemClicked(subscriber:Subscriber){
viewModel.initUpdateOrDelete(subscriber)    }

    private fun initView() {
        binding.recyclerSubscriber.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            displaySubscribers()

        }
    }

    private fun displaySubscribers() {
        viewModel.getAllSubscribers().observe(this, Observer {
            binding.recyclerSubscriber.adapter = SubscriberListAdapter(it) {subscriberClicked : Subscriber ->listItemClicked(subscriberClicked)}
//            binding.recyclerSubscriber.adapter = SubscriberListAdapter(it, object:SubscriberListAdapter.OnItemClickListener{
//                override fun onItemClick(pos: Int) {
//                    Toast.makeText(this@MainActivity, "", Toast.LENGTH_SHORT).show()
//                }

//            })
        })
    }
}