package com.marfarijj.roomexample.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscribers(subscriber: Subscriber)

    @Query("Delete from Subscriber")
    suspend fun deleteAllSubscriber()

    @Query("Select * from Subscriber")
    fun getAllSubscribers() : Flow<List<Subscriber>>

}