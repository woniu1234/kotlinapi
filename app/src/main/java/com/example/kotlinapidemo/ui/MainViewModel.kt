package com.example.kotlinapidemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinapidemo.base.StateLiveData
import com.example.kotlinapidemo.beans.TestDataBean
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainModel by lazy {
        MainModel()
    }
    val data = StateLiveData<List<TestDataBean>>()

    fun testData() {
        viewModelScope.launch {
            mainModel.getTestData(data)
        }
    }

}