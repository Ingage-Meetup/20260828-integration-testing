using System.Text.Json;

namespace IntegrationTesting;

public class UserService(IJsonPlaceholderClient jsonPlaceholderClient) : IUserService
{
    private readonly JsonSerializerOptions options = new()
    {
        PropertyNameCaseInsensitive = true
    };

    public async Task<List<BaseUser>> GetAllAsync()
    {
        var content = await jsonPlaceholderClient.GetAsync("/users", null);
        return JsonSerializer.Deserialize<List<BaseUser>>(content, options) ?? [];
    }

    public async Task<BaseUser?> GetByIdAsync(string id)
    {
        var content = await jsonPlaceholderClient.GetAsync("/users", null);
        var allUsers = JsonSerializer.Deserialize<List<BaseUser>>(content, options) ?? [];
        var user = allUsers.FirstOrDefault(u => u.Id.ToString() == id);
        return user;
    }

    public async Task<List<AddressUser>> GetNearbyAsync(double lat, double lng, double miles)
    {
        var content = await jsonPlaceholderClient.GetAsync("/users", null);
        var allUsers = JsonSerializer.Deserialize<List<AddressUser>>(content, options) ?? [];
        return [.. allUsers.Where(user =>
            user.Address?.Geo != null &&
            DistanceUtils.CalculateDistance(lat, lng, user.Address.Geo.Lat, user.Address.Geo.Lng) <= miles
        )];
    }
}