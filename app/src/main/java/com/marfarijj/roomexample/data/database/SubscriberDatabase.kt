package com.marfarijj.roomexample.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class SubscriberDatabase : RoomDatabase() {

    abstract var subscriberDao: SubscriberDao


    companion object {
        private var INSTANCE: SubscriberDatabase? = null

        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        SubscriberDatabase::class.java,
                        "subscriber_database"
                    ).build()
                }
                return instance
            }

        }
    }
}