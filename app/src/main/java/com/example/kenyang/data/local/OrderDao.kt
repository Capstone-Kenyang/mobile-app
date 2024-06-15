package com.example.kenyang.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kenyang.data.dataclass.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order)

    @Query("SELECT id FROM order_table")
    fun getAllOrderIds(): LiveData<List<String>>

    @Query("SELECT * FROM order_table")
    fun getAllOrder(): LiveData<List<Order>>

    @Update
    suspend fun update(note: Order)
    @Delete
    suspend fun delete(note: Order)

    @Query("DELETE FROM order_table")
    suspend fun deleteAllOrders()
}