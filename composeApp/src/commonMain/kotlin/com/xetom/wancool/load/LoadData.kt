package com.xetom.wancool.load


data class LoadData(
    val state: State, val result: Result
) {

    sealed interface State
    object Loading : State
    object Complete : State

    sealed interface Result
    object Error : Result
    object Success : Result

    companion object {
        fun ofLoading() = LoadData(Loading, Success)
        fun ofError() = LoadData(Complete, Error)
        fun ofSuccess() = LoadData(Complete, Success)
        fun ofComplete(result: Result) = LoadData(Complete, result)
    }
}

fun LoadData.isSuccess() = this.state == LoadData.Complete && this.result == LoadData.Success
fun LoadData.isLoading() = this.state == LoadData.Loading