package com.example.integrationtesting

object DistanceUtils {
    @JvmStatic
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadiusMiles = 3958.8

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val originLat = Math.toRadians(lat1)
        val destinationLat = Math.toRadians(lat2)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(originLat) * Math.cos(destinationLat) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.asin(Math.sqrt(a))

        return earthRadiusMiles * c
    }
}
