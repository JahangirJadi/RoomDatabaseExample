package com.marfarijj.roomexample.data.repository

import com.marfarijj.roomexample.data.database.Subscriber
import com.marfarijj.roomexample.data.database.SubscriberDao
import com.marfarijj.roomexample.util.Event

class SubscriberRepository(private val subscriberDao: SubscriberDao)
{
    val subscribers = subscriberDao.getAllSubscribers()

    suspend fun insertSubscriber(subscriber: Subscriber){
        subscriberDao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber){
        subscriberDao.updateSubscriber(subscriber)

    }

    suspend fun deleteSubscriber(subscriber: Subscriber){
        subscriberDao.deleteSubscribers(subscriber)
    }

    suspend fun deleteAllSubscriber(){
        subscriberDao.deleteAllSubscriber()
    }
}