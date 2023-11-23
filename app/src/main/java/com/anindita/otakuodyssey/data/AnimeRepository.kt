package com.anindita.otakuodyssey.data

import com.anindita.otakuodyssey.model.AnimeData
import com.anindita.otakuodyssey.model.AboutAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AnimeRepository {
    private val anime = mutableListOf<AboutAnime>()

    init {
        if (anime.isEmpty()) {
            AnimeData.anime.forEach {
                anime.add(AboutAnime(it, false))
            }
        }
    }

    fun getAllAnime(): Flow<List<AboutAnime>> {
        return flowOf(anime)
    }

    fun getAnimeById(animeId: String): AboutAnime {
        return anime.first {
            it.anime.id == animeId
        }
    }

    fun updateAnime(aboutAnime: AboutAnime) {
        val index = anime.indexOfFirst { it.anime.id == aboutAnime.anime.id }
        if (index != -1) {
            anime[index] = aboutAnime
        }
    }

    fun searchAnime(query: String): Flow<List<AboutAnime>> {
        return getAllAnime()
            .map { animes ->
                animes.filter { anime ->
                    anime.anime.title.contains(query, ignoreCase = true)
                }
            }
    }

    fun getFavoriteAnime(): Flow<List<AboutAnime>> {
        return getAllAnime().map { animes ->
            animes.filter { anime ->
                anime.isFav
            }
        }
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}