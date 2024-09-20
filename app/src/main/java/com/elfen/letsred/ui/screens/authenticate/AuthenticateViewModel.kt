package com.elfen.letsred.ui.screens.authenticate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.elfen.letsred.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: SessionRepository
): ViewModel() {
    private val code = savedStateHandle.get<String>("code")
    private val _done = MutableStateFlow(false)
    val done = _done.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.validateToken(code!!)
        }.invokeOnCompletion {
            _done.update { true }
        }
    }
}