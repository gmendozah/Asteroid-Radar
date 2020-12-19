package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val database: AsteroidDatabase) {

    /**
     * A list of [Asteroid] that can be shown on the screen.
     */
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    /**
     * Refresh the asteroids stored in the offline cache.
     */
    suspend fun refreshAsteroids(startDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            val response = Network.asteroidService.getAsteroidList(startDate, endDate).await()
            database.asteroidDao.insertAll(*response.asDatabaseModel())
        }
    }
}
