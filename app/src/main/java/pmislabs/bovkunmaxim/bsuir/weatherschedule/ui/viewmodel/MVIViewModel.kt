package pmislabs.bovkunmaxim.bsuir.weatherschedule.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.ParametersDefinition

interface Container<S, I, E> {
    fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit)
    val states: StateFlow<S>
    fun intent(intent: I)
}

abstract class MVIViewModel<S, I, E>(initial: S) : ViewModel(), Container<S, I, E> {

    private val _states: MutableStateFlow<S> = MutableStateFlow(initial)
    private val _events = Channel<E>()

    // должны быть final!
    final override val states = _states.asStateFlow()
    final override fun intent(intent: I) {
        viewModelScope.launch { reduce(intent) }
    }
    final override fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit) {
        launch {
            for (event in _events) {
                onEvent(event)
            }
        }
        onSubscribe()
    }

    protected fun event(event: E) {
        viewModelScope.launch { _events.send(event) }
    }
    protected fun state(block: S.() -> S) = run { _states.value = _states.value.block() }
    protected open fun CoroutineScope.onSubscribe() = Unit
    protected abstract suspend fun reduce(intent: I)
}

@Composable
fun <S, A> Container<S, *, A>.subscribe(onEvent: suspend (A) -> Unit): State<S> { // 1
    val state = states.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                subscribe(onEvent)
            }
        }
    }
    return state
}

// это для коина
@Composable
inline fun <reified T, S, I, E> container( // 2
    noinline params: ParametersDefinition? = null,
): Container<S, I, E> where T : Container<S, I, E>, T : ViewModel = getViewModel<T>(parameters = params)
