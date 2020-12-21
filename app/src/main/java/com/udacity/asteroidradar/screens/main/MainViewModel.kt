package com.udacity.asteroidradar.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val startDate = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            val endDate = dateFormat.format(calendar.time)
            Timber.e(startDate)
            Timber.e(endDate)
            asteroidRepository.refreshAsteroids(startDate, endDate)
            asteroidRepository.refreshImageOfTheDay()
        }
    }

    val asteroids = asteroidRepository.asteroids

    val imageOfTheDay = asteroidRepository.imageOfTheDay
}