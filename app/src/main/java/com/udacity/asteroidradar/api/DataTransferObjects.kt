package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.database.DatabaseAsteroid
import org.json.JSONObject

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
