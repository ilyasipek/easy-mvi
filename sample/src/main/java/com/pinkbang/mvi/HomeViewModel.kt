package com.pinkbang.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkbang.mvi.ui.HomeContract.SideEffect
import com.pinkbang.mvi.ui.HomeContract.UiAction
import com.pinkbang.mvi.ui.HomeContract.UiState

class HomeViewModel : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.OnIncreaseCountClick -> increaseCount()
            UiAction.OnDecreaseCountClick -> onDecreaseCountClick()
        }
    }

    private fun increaseCount() {
        updateUiState { copy(count = count + 1) }
    }

    private fun onDecreaseCountClick() {
        if (uiState.value.count > 0) {
            updateUiState { copy(count = count - 1) }
        } else {
            viewModelScope.emitSideEffect(SideEffect.ShowCountCanNotBeNegativeToast)
        }
    }
}

private fun initialUiState(): UiState = UiState(count = 0)
