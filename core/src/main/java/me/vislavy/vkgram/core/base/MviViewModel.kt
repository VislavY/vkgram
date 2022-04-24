package me.vislavy.vkgram.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class MviViewModel<State, Event, Action>(initialState: State) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<Action?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val action = _action.asSharedFlow()

    protected var mutableState: State
        get() = _state.value
        set(value) {
            _state.value = value
        }

    protected var mutableAction: Action?
        get() = _action.replayCache.last()
        set(value) {
            _action.tryEmit(value)
        }

    abstract fun onEvent(event: Event)
}