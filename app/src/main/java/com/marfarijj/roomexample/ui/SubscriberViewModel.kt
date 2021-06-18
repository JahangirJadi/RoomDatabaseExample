package com.marfarijj.roomexample.ui

import androidx.lifecycle.*
import com.marfarijj.roomexample.data.database.Subscriber
import com.marfarijj.roomexample.data.repository.SubscriberRepository
import com.marfarijj.roomexample.util.Event
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubscriberViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {
    var isUpdateOrDeleteEnabled = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber
    val subscribers = repository.subscribers
//    val subscribersList

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val btnAddUpdateSubscriber = MutableLiveData<String>()
    val btnClearAllSubscriber = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
    get() = statusMessage

    init {
        btnAddUpdateSubscriber.value = "Save"
        btnClearAllSubscriber.value = "Clear All"
    }

    fun addOrUpdateSubscriber() {

        if (isUpdateOrDeleteEnabled) {
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            updateSubscriber(subscriberToUpdateOrDelete)

        } else {

            val name = inputName.value
            val email = inputEmail.value

            insertSubscriber(Subscriber(0, name.toString(), email.toString()))



            inputEmail.value = ""
            inputName.value = ""
        }


    }

    fun deleteOrClearSubscriber() {
        viewModelScope.launch {

            repository.deleteAllSubscriber()
        }
    }


    fun insertSubscriber(subscriber: Subscriber): Job =
        viewModelScope.launch {
            if (isUpdateOrDeleteEnabled) {
                repository.updateSubscriber(subscriber)
                isUpdateOrDeleteEnabled = false
                btnAddUpdateSubscriber.value = "Save"
                btnClearAllSubscriber.value = "Clear All"
                inputName.value = ""
                inputEmail.value = ""


            } else {
                repository.insertSubscriber(subscriber)
                statusMessage.value = Event("Subscriber inserted successfully")
            }
        }

    fun updateSubscriber(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.updateSubscriber(subscriber)
            isUpdateOrDeleteEnabled = false
            btnAddUpdateSubscriber.value = "Save"
            btnClearAllSubscriber.value = "Clear All"
            inputName.value = ""
            inputEmail.value = ""
            statusMessage.value = Event("Subscriber updated successfully")

        }


    fun clearAll(): Job =
        viewModelScope.launch {
            if (isUpdateOrDeleteEnabled) {
                repository.deleteSubscriber(subscriberToUpdateOrDelete)
                isUpdateOrDeleteEnabled = false
                btnAddUpdateSubscriber.value = "Save"
                btnClearAllSubscriber.value = "Clear All"
                inputName.value = ""
                inputEmail.value = ""
                statusMessage.value = Event("Subscriber deleted successfully")

            } else {
                repository.deleteAllSubscriber()
                statusMessage.value = Event("All Subscribers deleted successfully")

            }


        }

    fun getAllSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        inputEmail.value = subscriber.email
        inputName.value = subscriber.name

        btnAddUpdateSubscriber.value = "Update"
        btnClearAllSubscriber.value = "Delete"
        isUpdateOrDeleteEnabled = true
        subscriberToUpdateOrDelete = subscriber

    }
}