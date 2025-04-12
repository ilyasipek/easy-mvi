package com.pinkbang.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVI<UiState, UiAction, SideEffect> {
    val uiState: StateFlow<UiState>

    val currentUiState: UiState

    val sideEffect: Flow<SideEffect>

    fun onAction(uiAction: UiAction)

    fun updateUiState(block: UiState.() -> UiState)

    fun updateUiState(newUiState: UiState)

    fun CoroutineScope.emitSideEffect(effect: SideEffect)
}

/**
 * A helper function to destructure the [MVI] into its individual parts.
 *
 * Example usage:
 * ```
 * val (uiState, onAction, sideEffect) = mvi.unpack()
 * ```
 * */
@Stable
@Composable
fun <UiState, UiAction, SideEffect> MVI<UiState, UiAction, SideEffect>.unpack() =
    Triple(uiState.collectAsState().value, ::onAction, sideEffect)
