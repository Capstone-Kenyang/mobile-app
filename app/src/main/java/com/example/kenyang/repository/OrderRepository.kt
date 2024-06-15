package com.example.kenyang.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.data.local.OrderDao
import com.example.kenyang.data.local.OrderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository(application: Application) {
    private val mOrderDao: OrderDao

    init {
        val db = OrderDatabase.getDatabase(application)
        mOrderDao = db.orderDao()
    }

    suspend fun insertOrder(order: Order) {
        withContext(Dispatchers.IO) {
            mOrderDao.insert(order)
        }
    }

    suspend fun deleteOrder(order: Order) {
        withContext(Dispatchers.IO) {
            mOrderDao.delete(order)
        }
    }

    suspend fun updateOrder(order: Order) {
        withContext(Dispatchers.IO) {
            mOrderDao.update(order)
        }
    }

    fun getAllOrder(): LiveData<List<Order>> {
        return mOrderDao.getAllOrder()

    }

    fun getAllOrderIds(): LiveData<List<String>> {
        return mOrderDao.getAllOrderIds()
    }
}