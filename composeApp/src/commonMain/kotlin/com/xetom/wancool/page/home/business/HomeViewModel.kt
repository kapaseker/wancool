package com.xetom.wancool.page.home.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xetom.wancool.api.DogApi
import com.xetom.wancool.api.IpApi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getStringArray
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.home_columns

class HomeViewModel : ViewModel() {

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip.asStateFlow()

    private val _breeds = MutableStateFlow<Map<String, Array<String>>>(mapOf())
    val breeds: StateFlow<Map<String, Array<String>>> = _breeds.asStateFlow()

    private val _columns = MutableStateFlow<ImmutableList<String>>(persistentListOf())
    val columns: StateFlow<ImmutableList<String>> = _columns.asStateFlow()

    private fun loadHomeData() {
        viewModelScope.launch(Dispatchers.Default) {
            getStringArray(Res.array.home_columns).let { res ->
                _columns.update { res.toImmutableList() }
            }
            _ip.update { IpApi.currentIp() }
            _breeds.update { DogApi.breeds() }
        }
    }

    init {
        loadHomeData()
    }
}