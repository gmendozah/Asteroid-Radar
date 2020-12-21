package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AsteroidRepository(private val database: AsteroidDatabase) {

    /**
     * A list of [Asteroid] that can be shown on the screen.
     */
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    val imageOfTheDay: LiveData<PictureOfDay> =
        Transformations.map(database.asteroidDao.getImageOfTheDay()) {
            it.asDomainModel()
        }

    /**
     * Refresh the asteroids stored in the offline cache.
     */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val response = Network.asteroidService.getAsteroidList(/*startDate, endDate*/).await()
            database.asteroidDao.insertAll(*response.asDatabaseModel())
        }
    }


    /**
     * Refresh the image of the day stored in the offline cache.
     */
    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.IO) {
            val response = Network.asteroidService.getImageOfTheDay().await()
            database.asteroidDao.insertImageOfTheDay(response.asDatabaseModel())
        }
    }
}
