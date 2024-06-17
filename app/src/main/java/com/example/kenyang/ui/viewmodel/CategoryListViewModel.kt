package com.example.kenyang.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.converter.sortListByRating
import com.example.kenyang.data.dataclass.Menu

class CategoryListViewModel : ViewModel() {
    private val _sortKey = MutableLiveData<String>()

    private val _menus = MutableLiveData<List<Menu>>()
    val menus = _menus

    fun updateSortKeyToDistance() {
        _sortKey.value = DISTANCE
    }

    fun updateSortKeyToRating() {
        _sortKey.value = RATING
    }

    fun updateMenuListByFilter(list: List<Menu>) {
        if (_sortKey.value == RATING) {
            _menus.value = sortListByRating(list)
        } else {
            _menus.value = sortListByDistance(list)
        }
    }

    companion object {
        const val RATING = "rating"
        const val DISTANCE = "distance"
    }
}