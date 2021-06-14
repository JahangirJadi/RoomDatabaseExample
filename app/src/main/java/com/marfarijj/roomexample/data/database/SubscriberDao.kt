package com.marfarijj.roomexample.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) : Int

    @Delete
    suspend fun deleteSubscribers(subscriber: Subscriber) : Int

    @Query("Delete from table_subscriber")
    suspend fun deleteAllSubscriber()

    @Query("Select * from table_subscriber")
    fun getAllSubscribers() : Flow<List<Subscriber>>

}