package com.anindita.otakuodyssey.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anindita.otakuodyssey.data.AnimeRepository
import com.anindita.otakuodyssey.model.AboutAnime
import com.anindita.otakuodyssey.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailAnimeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    data class ViewState(
        val uiState: UiState<AboutAnime>,
        val isFavorite: Boolean
    )

    private val _viewState: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState(uiState = UiState.Loading, isFavorite = false))
    val viewState: StateFlow<ViewState>
        get() = _viewState

    fun getAnimeById(animeId: String) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(uiState = UiState.Loading)
            val aboutAnime = repository.getAnimeById(animeId)
            _viewState.value = _viewState.value.copy(uiState = UiState.Success(aboutAnime), isFavorite = aboutAnime.isFav)
        }
    }

    fun toggleFavorite(animeId: String) {
        viewModelScope.launch {
            val aboutAnime = repository.getAnimeById(animeId)
            aboutAnime.isFav = !aboutAnime.isFav
            repository.updateAnime(aboutAnime)
            _viewState.value = _viewState.value.copy(isFavorite = aboutAnime.isFav)
            Log.d("DetailAnimeViewModel", "isFavorite: ${aboutAnime.isFav}")
        }
    }

    fun getFavoriteAnime(): Flow<List<AboutAnime>> {
        return repository.getFavoriteAnime()
    }
}