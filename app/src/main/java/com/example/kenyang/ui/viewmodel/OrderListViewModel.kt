package com.example.kenyang.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.repository.OrderRepository

class OrderListViewModel(application: Application) : ViewModel() {

    private val mOrderRepository = OrderRepository(application)

    fun getAllOrder(): LiveData<List<Order>> {
        return mOrderRepository.getAllOrder()
    }

}