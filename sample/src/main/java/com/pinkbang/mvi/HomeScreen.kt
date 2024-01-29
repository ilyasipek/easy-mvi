package com.pinkbang.mvi

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pinkbang.mvi.ui.HomeContract.SideEffect
import com.pinkbang.mvi.ui.HomeContract.UiAction
import com.pinkbang.mvi.ui.HomeContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen() {
    val vm = remember { HomeViewModel() }

    val uiState by vm.uiState.collectAsState()
    val sideEffect = vm.sideEffect
    val onAction = vm::onAction
    HomeScreen(uiState, sideEffect, onAction)
}

@Composable
fun HomeScreen(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onAction: (UiAction) -> Unit,
) {
    val context = LocalContext.current

    CollectSideEffect(sideEffect) {
        when (it) {
            SideEffect.ShowCountCanNotBeNegativeToast -> {
                Toast.makeText(context, "Count can't be less than 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Count: ${uiState.count}",
                color = Color.Black,
            )
            Row(
                Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = { onAction(UiAction.OnIncreaseCountClick) }) {
                    Text("Increase")
                }
                Button(onClick = { onAction(UiAction.OnDecreaseCountClick) }) {
                    Text("Decrease")
                }
            }
        }
    }
}
