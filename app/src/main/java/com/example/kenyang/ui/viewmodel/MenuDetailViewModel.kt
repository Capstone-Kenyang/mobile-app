package com.example.kenyang.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.repository.OrderRepository
import kotlinx.coroutines.launch

class MenuDetailViewModel(application: Application) : ViewModel() {

    private val mOrderRepository = OrderRepository(application)
    fun insertOrder(order: Order) {
        viewModelScope.launch {
            mOrderRepository.insertOrder(order)
        }
    }

    fun getAllOrderIds(): LiveData<List<String>> {
        return mOrderRepository.getAllOrderIds()
    }
}