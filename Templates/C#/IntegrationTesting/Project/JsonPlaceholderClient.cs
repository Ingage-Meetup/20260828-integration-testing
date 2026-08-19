namespace IntegrationTesting;

public class JsonPlaceholderClient(IHttpClientFactory factory) : IJsonPlaceholderClient
{
    private readonly HttpClient httpClient = factory.CreateClient("MyApiClient");

    public async Task<string> GetAsync(string path, string? queryString)
    {
        var url = string.IsNullOrEmpty(queryString) ? path : $"{path}?{queryString}";
        var response = await httpClient.GetAsync(url);
        response.EnsureSuccessStatusCode();
        return await response.Content.ReadAsStringAsync();
    }
}

public class HttpClientSettings
{
    required public string BaseUrl { get; set; }
}