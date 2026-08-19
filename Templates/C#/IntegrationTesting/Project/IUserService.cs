namespace IntegrationTesting;

public interface IUserService
{
    Task<List<BaseUser>> GetAllAsync();
    Task<BaseUser?> GetByIdAsync(string id);
    Task<List<AddressUser>> GetNearbyAsync(double lat, double lng, double miles);
}