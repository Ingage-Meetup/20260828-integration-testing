package com.example.integrationtesting;

public class DistanceUtils {
    private DistanceUtils() {}

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Validate latitude and longitude ranges
        if (lat1 < -90 || lat1 > 90 || lat2 < -90 || lat2 > 90 ||
            lon1 < -180 || lon1 > 180 || lon2 < -180 || lon2 > 180) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90, longitude between -180 and 180.");
        }

        double R = 3963.19; // Earth's radius in miles
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
                   Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in miles
    }

    private static double toRadians(double degrees) { 
        return degrees * Math.PI / 180.0;
    }
}
