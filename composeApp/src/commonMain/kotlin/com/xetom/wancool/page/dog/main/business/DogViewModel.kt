package com.xetom.wancool.page.dog.main.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xetom.wancool.api.DogApi
import com.xetom.wancool.load.LoadData
import com.xetom.wancool.nav.DogNav
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DogUIState(
    val load: LoadData,
    val dogs: ImmutableList<String>,
)

class DogViewModel(savedState: SavedStateHandle) : ViewModel() {

    private val arg = DogNav.unpack(savedState)

    private val _dogs = MutableStateFlow(DogUIState(LoadData.ofLoading(), persistentListOf()))
    val dogs: StateFlow<DogUIState> = _dogs.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    init {
        loadDogs()
    }

    private fun loadDogs() {
        val main = arg.main
        val sub = arg.sub
        _title.update { string ->  main + if (sub.isNotEmpty()) "/$sub" else "" }
        viewModelScope.launch(Dispatchers.Default) {
            _dogs.update { DogUIState(LoadData.ofLoading(), persistentListOf())  }
            val dogList = if (sub.isEmpty()) {
                DogApi.mainDogs(main)
            } else {
                DogApi.subDogs(main, sub)
            }
            _dogs.update { DogUIState(LoadData.ofComplete(dogList.first), dogList.second.toImmutableList())  }
        }
    }
}