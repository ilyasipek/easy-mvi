package com.pinkbang.mvi

interface HomeContract {
    data class UiState(val count: Int)

    sealed interface UiAction {
        object OnIncreaseCountClick : UiAction
        object OnDecreaseCountClick : UiAction
    }

    sealed interface SideEffect {
        object ShowCountCanNotBeNegativeToast : SideEffect
    }
}
