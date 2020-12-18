package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val database: AsteroidDatabase) {

    /**
     * A list of [Asteroid] that can be shown on the screen.
     */
    val videos: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getAsteroids()) {
        it.asDomainModel()
    }

    /**
     * Refresh the asteroids stored in the offline cache.
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            /*val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())*/
        }
    }
}
