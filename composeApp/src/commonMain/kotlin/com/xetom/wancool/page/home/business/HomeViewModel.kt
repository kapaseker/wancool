package com.xetom.wancool.page.home.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xetom.wancool.api.DogApi
import com.xetom.wancool.api.IpApi
import com.xetom.wancool.load.LoadData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getStringArray
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.home_columns


data class BreedUIState(
    val load: LoadData,
    val breeds: ImmutableMap<String, Array<String>>,
)

class HomeViewModel : ViewModel() {

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip.asStateFlow()

    private val _breeds = MutableStateFlow<BreedUIState>(BreedUIState(LoadData.ofLoading(), persistentMapOf()))
    val breeds: StateFlow<BreedUIState> = _breeds.asStateFlow()

    private val _columns = MutableStateFlow<ImmutableList<String>>(persistentListOf())
    val columns: StateFlow<ImmutableList<String>> = _columns.asStateFlow()

    private fun loadHomeData() {
        viewModelScope.launch(Dispatchers.Default) {
            getStringArray(Res.array.home_columns).let { res ->
                _columns.update { res.toImmutableList() }
            }
            _ip.update { IpApi.currentIp() }
        }
        viewModelScope.launch(Dispatchers.Default) {
            _breeds.update { BreedUIState(LoadData.ofLoading(), persistentMapOf()) }
            DogApi.breeds().let { (r, v) ->
                _breeds.update { BreedUIState(LoadData.ofComplete(r), v.toImmutableMap()) }
            }
        }
    }

    init {
        loadHomeData()
    }
}