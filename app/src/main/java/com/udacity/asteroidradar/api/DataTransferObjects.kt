package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.DatabasePictureOfDay
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class NetworkImageOfTheDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String,
)

/**
 * Convert Network results to database objects
 */
fun String.asDatabaseModel(): Array<DatabaseAsteroid> {
    return parseAsteroidsJsonResult(JSONObject(this)).map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
        )
    }.toTypedArray()
}

fun NetworkImageOfTheDay.asDatabaseModel(): DatabasePictureOfDay {
    return DatabasePictureOfDay(
        url = url,
        title = title,
        mediaType = mediaType,
    )
}
