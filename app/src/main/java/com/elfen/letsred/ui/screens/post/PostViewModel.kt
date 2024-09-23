package com.elfen.letsred.ui.screens.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.elfen.letsred.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    postRepository: PostRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<PostRoute>()
    private val post = postRepository.postById(route.id)
    private val comments = postRepository.commentsById(route.id)

    val state = combine(post, comments) { post, comments ->
        if (post == null)
            return@combine PostUiState()

        PostUiState(post, comments)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PostUiState()
    )

    init {
        viewModelScope.launch {
            postRepository.fetchPostComments(route.id)
        }
    }
}