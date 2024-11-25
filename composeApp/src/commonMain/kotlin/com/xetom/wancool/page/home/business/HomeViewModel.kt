package com.xetom.wancool.page.home.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xetom.wancool.api.CatApi
import com.xetom.wancool.api.CatBreed
import com.xetom.wancool.api.DogApi
import com.xetom.wancool.api.IpApi
import com.xetom.wancool.load.LoadData
import kotlinx.collections.immutable.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getStringArray
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.home_columns


data class DogBreedUIState(
    val load: LoadData,
    val breeds: ImmutableMap<String, Array<String>>,
)

data class CatBreedUIState(
    val load: LoadData,
    val breeds: ImmutableList<CatBreed>,
)

class HomeViewModel : ViewModel() {

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip.asStateFlow()

    private val _dogBreeds = MutableStateFlow<DogBreedUIState>(DogBreedUIState(LoadData.ofLoading(), persistentMapOf()))
    val dogBreeds: StateFlow<DogBreedUIState> = _dogBreeds.asStateFlow()

    private val _catBreeds = MutableStateFlow(CatBreedUIState(LoadData.ofLoading(), persistentListOf()))
    val catBreeds: StateFlow<CatBreedUIState> = _catBreeds.asStateFlow()

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
            _dogBreeds.update { DogBreedUIState(LoadData.ofLoading(), persistentMapOf()) }
            DogApi.breeds().let { (r, v) ->
                _dogBreeds.update { DogBreedUIState(LoadData.ofComplete(r), v.toImmutableMap()) }
            }
        }

        viewModelScope.launch(Dispatchers.Default) {
            _catBreeds.update { CatBreedUIState(LoadData.ofLoading(), persistentListOf()) }
            CatApi.breeds().let { (r, v) ->
                _catBreeds.update { CatBreedUIState(LoadData.ofComplete(r), v.toImmutableList()) }
            }
        }
    }

    init {
        loadHomeData()
    }
}