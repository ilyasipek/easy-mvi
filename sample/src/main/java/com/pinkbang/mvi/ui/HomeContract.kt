package com.pinkbang.mvi.ui

interface HomeContract {
    data class UiState(
        val count: Int,
    )

    sealed interface UiAction {
        object OnIncreaseCountClick : UiAction
        object OnDecreaseCountClick : UiAction
    }

    sealed interface UiEffect {
        object ShowCountCanNotBeNegativeToast : UiEffect
    }
}
