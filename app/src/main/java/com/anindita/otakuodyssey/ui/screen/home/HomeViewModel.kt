package com.anindita.otakuodyssey.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anindita.otakuodyssey.data.AnimeRepository
import com.anindita.otakuodyssey.model.AboutAnime
import com.anindita.otakuodyssey.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AnimeRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<AboutAnime>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<AboutAnime>>>
        get() = _uiState

    fun getAllAnime() {
        viewModelScope.launch {
            repository.getAllAnime()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favAnime ->
                    _uiState.value = UiState.Success(favAnime)
                }
        }
    }

    fun searchAnime(query: String) {
        viewModelScope.launch {
            repository.searchAnime(query)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { searchedAnime ->
                    _uiState.value = UiState.Success(searchedAnime)
                }
        }
    }
}