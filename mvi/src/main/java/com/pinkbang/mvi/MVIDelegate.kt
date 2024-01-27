package com.pinkbang.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MVIDelegate<UiState, UiAction, UiEffect> internal constructor(
    initialUiState: UiState,
) : MVI<UiState, UiAction, UiEffect> {

    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    override val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    override fun onAction(uiAction: UiAction) {}

    override fun updateUiState(newUiState: UiState) {
        _uiState.update { newUiState }
    }

    override fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    override fun CoroutineScope.emitUiEffect(uiEffect: UiEffect) {
        this.launch { _uiEffect.send(uiEffect) }
    }
}

fun <UiState, UiAction, UiEffect> mvi(
    initialUiState: UiState,
): MVI<UiState, UiAction, UiEffect> = MVIDelegate(initialUiState)
