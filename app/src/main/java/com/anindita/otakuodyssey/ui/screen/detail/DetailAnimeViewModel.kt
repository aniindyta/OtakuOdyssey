package com.anindita.otakuodyssey.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anindita.otakuodyssey.data.AnimeRepository
import com.anindita.otakuodyssey.model.Anime
import com.anindita.otakuodyssey.model.AboutAnime
import com.anindita.otakuodyssey.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailAnimeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AboutAnime>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AboutAnime>>
        get() = _uiState

    fun getAnimeById(animeId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAnimeById(animeId))
        }
    }

    fun addToFav(anime: Anime, isFavAnime: Boolean) {
        viewModelScope.launch {
            repository.updateFavAnime(anime.id, false)
        }
    }
}