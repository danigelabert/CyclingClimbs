package com.danigelabert.cyclingclimbs.ui

import org.osmdroid.api.IGeoPoint

class CustomGeoPoint(private val latitude: Double, private val longitude: Double) : IGeoPoint {
    override fun getLatitude(): Double {
        return latitude
    }

    override fun getLongitude(): Double {
        return longitude
    }

    fun getAltitude(): Double {
        // Not needed for your use case, return 0.0 as a placeholder
        return 0.0
    }

    fun setLatitude(latitude: Double) {
        // Not needed for your use case
    }

    fun setLongitude(longitude: Double) {
        // Not needed for your use case
    }

    fun setAltitude(altitude: Double) {
        // Not needed for your use case
    }

    fun clone(): IGeoPoint {
        return CustomGeoPoint(latitude, longitude)
    }

    override fun toString(): String {
        return "CustomGeoPoint(latitude=$latitude, longitude=$longitude)"
    }

    override fun getLatitudeE6(): Int {
        TODO("Not yet implemented")
    }

    override fun getLongitudeE6(): Int {
        TODO("Not yet implemented")
    }
}