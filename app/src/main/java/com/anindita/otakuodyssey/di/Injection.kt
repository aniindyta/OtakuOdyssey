package com.anindita.otakuodyssey.di

import com.anindita.otakuodyssey.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}