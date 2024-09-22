package com.elfen.letsred.ui.screens.saved

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.elfen.letsred.data.repository.FeedRepository
import com.elfen.letsred.models.FeedSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    feedRepository: FeedRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val route = savedStateHandle.toRoute<SavedRoute>()
    val lazyPosts = feedRepository.feedPagerByQuery(FeedSource.SavedPosts(route.username))
}