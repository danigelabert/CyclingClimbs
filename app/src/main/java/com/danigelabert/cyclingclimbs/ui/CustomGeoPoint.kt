package com.danigelabert.cyclingclimbs.ui

import org.osmdroid.api.IGeoPoint

class CustomGeoPoint(private val latitude: Double, private val longitude: Double) : IGeoPoint {
    override fun getLatitude(): Double {
        return latitude
    }

    override fun getLongitude(): Double {
        return longitude
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