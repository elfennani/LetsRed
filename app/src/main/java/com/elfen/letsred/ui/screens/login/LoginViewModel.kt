package com.elfen.letsred.ui.screens.login

import androidx.lifecycle.ViewModel
import com.elfen.letsred.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    fun initiate() = sessionRepository.initiateAuthentication()
}