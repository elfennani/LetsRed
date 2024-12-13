package com.elfen.letsred.ui.screens.post

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.elfen.letsred.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    postRepository: PostRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<PostRoute>()
    private val postDetails = postRepository.postDetailsById(route.id)

    val state = postDetails.map { (post, comments) ->
        PostUiState(post, comments)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PostUiState()
    )
}