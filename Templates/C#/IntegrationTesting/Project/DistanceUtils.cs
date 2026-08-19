
public static class DistanceUtils
{
    public static double CalculateDistance(double lat1, double lon1, double lat2, double lon2)
    {
        // Validate latitude and longitude ranges
        if (lat1 < -90 || lat1 > 90 || lat2 < -90 || lat2 > 90 ||
            lon1 < -180 || lon1 > 180 || lon2 < -180 || lon2 > 180)
        {
            throw new ArgumentOutOfRangeException("Latitude must be between -90 and 90, longitude between -180 and 180.");
        }

        const double R = 6371.0; // Earth's radius in kilometers
        double dLat = ToRadians(lat2 - lat1);
        double dLon = ToRadians(lon2 - lon1);

        double a = Math.Pow(Math.Sin(dLat / 2), 2) +
                   Math.Cos(ToRadians(lat1)) * Math.Cos(ToRadians(lat2)) *
                   Math.Pow(Math.Sin(dLon / 2), 2);

        double c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

    private static double ToRadians(double degrees) => degrees * Math.PI / 180.0;
}