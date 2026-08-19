namespace IntegrationTesting;

public interface IJsonPlaceholderClient
{
    Task<string> GetAsync(string path, string? queryString);
}