package com.pinkbang.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <SideEffect> CollectSideEffect(
    sideEffect: Flow<SideEffect>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = Dispatchers.Main.immediate,
    onSideEffect: suspend CoroutineScope.(effect: SideEffect) -> Unit,
) {
    LaunchedEffect(sideEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            if (context == EmptyCoroutineContext) {
                sideEffect.collect { onSideEffect(it) }
            } else {
                withContext(context) {
                    sideEffect.collect { onSideEffect(it) }
                }
            }
        }
    }
}
