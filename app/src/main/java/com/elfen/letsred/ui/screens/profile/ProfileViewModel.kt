package com.elfen.letsred.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.elfen.letsred.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val route = savedStateHandle.toRoute<ProfileRoute>()
    private val user = userRepository.userById(route.userId)

    val state = user.map {
        if(it == null)
            ProfileUiState.Error("Not Found")
        else
            ProfileUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ProfileUiState.Loading
    )

    init {
        viewModelScope.launch {
            userRepository.fetchUserByUsername(route.username)
        }
    }
}