package com.anindita.otakuodyssey.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anindita.otakuodyssey.data.AnimeRepository
import com.anindita.otakuodyssey.ui.screen.all.AllViewModel
import com.anindita.otakuodyssey.ui.screen.detail.DetailAnimeViewModel
import com.anindita.otakuodyssey.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AllViewModel::class.java)) {
            return AllViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailAnimeViewModel::class.java)) {
            return DetailAnimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}