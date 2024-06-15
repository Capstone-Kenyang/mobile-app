package com.example.kenyang.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kenyang.ui.viewmodel.MenuDetailViewModel
import com.example.kenyang.ui.viewmodel.OrderDetailViewModel
import com.example.kenyang.ui.viewmodel.OrderListViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuDetailViewModel::class.java)) {
            return MenuDetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(OrderListViewModel::class.java)) {
            return OrderListViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            return OrderDetailViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }

            return INSTANCE as ViewModelFactory
        }
    }
}