package com.xetom.wancool.page.home.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xetom.wancool.api.*
import com.xetom.wancool.load.LoadData
import com.xetom.wancool.load.isLoading
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

data class PictureUIState(
    val load: LoadData,
    val breeds: ImmutableList<PicsumItem>,
)

data class NarutoUIState(
    val load: LoadData,
    val narutos: ImmutableList<Character>,
)


sealed interface HomeIntent {
    data object MoreNaruto : HomeIntent
}

class HomeViewModel : ViewModel() {

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip.asStateFlow()

    private val _dogBreeds = MutableStateFlow<DogBreedUIState>(DogBreedUIState(LoadData.ofSuccess(), persistentMapOf()))
    val dogBreeds: StateFlow<DogBreedUIState> = _dogBreeds.asStateFlow()

    private val _catBreeds = MutableStateFlow(CatBreedUIState(LoadData.ofSuccess(), persistentListOf()))
    val catBreeds: StateFlow<CatBreedUIState> = _catBreeds.asStateFlow()

    private val _columns = MutableStateFlow<ImmutableList<String>>(persistentListOf())
    val columns: StateFlow<ImmutableList<String>> = _columns.asStateFlow()

    private val _picture = MutableStateFlow(PictureUIState(LoadData.ofSuccess(), persistentListOf()))
    val picture: StateFlow<PictureUIState> = _picture.asStateFlow()

    private var narutoPage: Int = 1;

    private val _narutos = MutableStateFlow(NarutoUIState(LoadData.ofSuccess(), persistentListOf()))
    val narutos: StateFlow<NarutoUIState> = _narutos.asStateFlow()

    infix fun want(intent: HomeIntent) {
        when (intent) {
            HomeIntent.MoreNaruto -> {
                moreNaruto()
            }
        }
    }

    private fun moreNaruto() {
        if (!_narutos.value.load.isLoading()) {

            val reset = narutoPage == 1

            viewModelScope.launch(Dispatchers.Default) {

                _narutos.update { NarutoUIState(LoadData.ofLoading(), it.narutos) }

                NarutoApi.characters(narutoPage, 50).let { (r, v) ->
                    narutoPage++
                    if (reset) {
                        _narutos.update { NarutoUIState(LoadData.ofComplete(r), v.characters.toImmutableList()) }
                    } else {
                        _narutos.update { NarutoUIState(LoadData.ofComplete(r), (it.narutos + v.characters).toImmutableList()) }
                    }
                }
            }
        }
    }

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

        viewModelScope.launch(Dispatchers.Default) {
            _picture.update { PictureUIState(LoadData.ofLoading(), persistentListOf()) }
            PicsumApi.list(1, 50).let { (r, v) ->
                _picture.update { PictureUIState(LoadData.ofComplete(r), v.toImmutableList()) }
            }
        }

        narutoPage = 1
        moreNaruto()
    }

    init {
        loadHomeData()
    }
}