package com.marfarijj.roomexample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.marfarijj.roomexample.data.database.Subscriber
import com.marfarijj.roomexample.data.repository.SubscriberRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubscriberViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    val subscribers = repository.subscribers
//    val subscribersList

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val btnAddUpdateSubscriber = MutableLiveData<String>()
    val btnClearAllSubscriber = MutableLiveData<String>()

    init {
        btnAddUpdateSubscriber.value = "Save"
        btnClearAllSubscriber.value = "Clear All"
    }

    fun addOrUpdateSubscriber() {
        val name = inputName.value
        val email = inputEmail.value

        insertSubscriber(Subscriber(0, name.toString(), email.toString()))

        inputEmail.value = ""
        inputName.value = ""
    }

    fun deleteOrClearSubscriber() {
        viewModelScope.launch {
            repository.deleteAllSubscriber()
        }
    }

    fun getSubscribers() {
        viewModelScope.launch {
            repository.subscribers
        }
    }

    fun insertSubscriber(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.insertSubscriber(subscriber)
        }

    fun updateSubscriber(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.updateSubscriber(subscriber)
        }

    fun deleteSubscriber(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.deleteSubscriber(subscriber)
        }

    fun clearAll(): Job =
        viewModelScope.launch {

            repository.deleteAllSubscriber()
        }

    fun getAllSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }
}