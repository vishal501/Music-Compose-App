package com.example.musicapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event: UIEvent, State: UIState, Effect: UIEffect>: ViewModel() {


    private val initialState : State by lazy { createInitialState() }


    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    val currentState = state.value

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.consumeAsFlow()

    init {
        subscribeEvents()
    }

    abstract fun createInitialState(): State

    /**
     * Handle each event
     */
    abstract fun handleEvent(event : Event)

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        launchInViewModelScope {
            event.collect {
                handleEvent(it)
            }
        }
    }


    fun setEvent(event: Event){
        launchInViewModelScope { _event.emit(event) }
    }

    fun setState(reduce: State.() -> State){
        val newState = _state.value.reduce()
        _state.value = newState
    }

    fun setEffect(effect: Effect){
        launchInViewModelScope { this._effect.send(effect) }
    }

    fun launchInViewModelScope(action: suspend () -> Unit){
        viewModelScope.launch { action() }
    }

}

interface UIEvent
interface UIEffect
interface UIState