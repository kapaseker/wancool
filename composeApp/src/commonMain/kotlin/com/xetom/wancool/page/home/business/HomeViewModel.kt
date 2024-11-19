package com.xetom.wancool.page.home.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xetom.wancool.api.DogApi
import com.xetom.wancool.api.IpApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip.asStateFlow()

    private val _breeds = MutableStateFlow<Map<String, Array<String>>>(mapOf())
    val breeds: StateFlow<Map<String, Array<String>>> = _breeds.asStateFlow()

    private fun loadHomeData() {
        viewModelScope.launch(Dispatchers.Default) {
            _ip.update { IpApi.currentIp() }
            _breeds.update { DogApi.breeds() }
        }
    }

    init {
        loadHomeData()
    }
}