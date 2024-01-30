# Easy-MVI ![latestVersion](https://img.shields.io/github/v/tag/ilyasipek/easy-mvi?display_name=tag)

## Intro

Easy-MVI is a simple, easy-to-use library that helps you to implement MVI architecture in your Android application.

## Concepts

UI State: Represents the current visible data and state on the screen.

Intent/Action: Represents user actions or interactions with the screen. for example Button click.

SideEffects: Represents a one time event like showing a Toast.

## Implementation

Add the Jitpack repository to your root build.gradle file. If you’re using the settings.gradle file, include it there.

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

Then add easy-mvi dependency to your module build.gradle file.

```groovy
implementation "com.github.ilyasipek:easy-mvi:$versionName"
```

### Usage

All you need is to add **MVI** interface to your ViewModel and use **mvi()** delegate to implement it.
Also give the **mvi()** an initial state. That's it! 🔥

```kotlin
class HomeViewModel : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.OnIncreaseCountClick -> increaseCount()
            UiAction.OnDecreaseCountClick -> onDecreaseCountClick()
        }
    }

    private fun increaseCount() {
        // here is how to update state 
        updateUiState { copy(count = count + 1) }
    }

    private fun onDecreaseCountClick() {
        if (uiState.value.count > 0) {
            updateUiState { copy(count = count - 1) }
        } else {
            // here is how to emit side effect
            viewModelScope.emitSideEffect(SideEffect.ShowCountCanNotBeNegativeToast)
        }
    }
}

private fun initialUiState(): UiState = UiState(count = 0)
```

Than you can observe the uiState, sideEffects and fire actions in your screen.

```kotlin
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
    // collect side effects
    CollectSideEffect(sideEffect) {
        when (it) {
            SideEffect.ShowCountCanNotBeNegativeToast -> {
                Toast.makeText(context, "Count can't be less than 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(/***/) {
        Text(
            text = "Count: ${uiState.count}",
        )
        Button(onClick = { onAction(UiAction.OnIncreaseCountClick) }) {
            Text("Increase")
        }
    }
}
```

Just in case you wonder here is our UiState, UiAction and SideEffect classes.

```kotlin
data class UiState(val count: Int)

sealed interface UiAction {
    object OnIncreaseCountClick : UiAction
    object OnDecreaseCountClick : UiAction
}

sealed interface SideEffect {
    object ShowCountCanNotBeNegativeToast : SideEffect
}
```

That's it, if you want to learn more details check [this article](https://medium.com/p/f2aa1a842b73/edit).

## Authors

**Ilyas Ipek**

- Medium: <a href="https://medium.com/@ilyas_ipek" target="_blank">@ilyasipek</a>
- Github: <a href="https://github.com/ilyasipek" target="_blank">@ilyasipek</a>
- LinkedIn: <a href="https://www.linkedin.com/in/ilyas-ipek/" target="_blank">@ilyas-ipek</a>

## Show your support

⭐️ Give us a star if this project helped you! ⭐️

### [LICENSE](https://github.com/composeuisuite/ohteepee/blob/develop/LICENSE.md)
