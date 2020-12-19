package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.ImageOfTheDay

@Entity(tableName = "Asteroids")
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
)

@Entity(tableName = "ImageOfTheDay")
data class DatabaseImageOfTheDay constructor(
    @PrimaryKey
    val url: String,
    val copyright: String,
    val date: String,
    val explanation: String,
    val title: String,
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
        )
    }
}

fun DatabaseImageOfTheDay.asDomainModel(): ImageOfTheDay {
    return ImageOfTheDay(
        url = url,
        title = title,
        copyright = copyright,
        date = date,
        explanation = explanation,
    )
}
