using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using System.Text.Json.Nodes;
using GraphQL;
using IntegrationTesting;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using WireMock.Handlers;
using WireMock.Server;
using WireMock.Settings;


namespace TestProject;

public class IntegrationTests
{
    private WebApplicationFactory<Program> factory;
    private HttpClient client;
    private WireMockServer server;

    [OneTimeSetUp]
    public void ServiceRegistrationSetup()
    {
        // TODO figure out a better way to do the wiremockDir...
        string wiremockDir = Path.Combine(TestContext.CurrentContext.TestDirectory, "..", "..", "..", "wiremock");
        
        server = WireMockServer.Start(new WireMockServerSettings
        {
            ReadStaticMappings = true, // true= mock mode, false=capture mode

            Port = 9080,
            StartAdminInterface = true,
            FileSystemHandler = new LocalFileSystemHandler(wiremockDir),
            ProxyAndRecordSettings = new ProxyAndRecordSettings
            {
                Url = "https://jsonplaceholder.typicode.com",
                SaveMapping = true,
                SaveMappingToFile = true,
                ExcludedHeaders = ["traceparent"]
            }
        });

        factory = new WebApplicationFactory<Program>().WithWebHostBuilder(builder =>
        {
            builder.ConfigureAppConfiguration((ctx, config) =>
            {
                config.AddJsonFile("appsettings.Test.json");
            });
        });

        client = factory.CreateClient();
    }

    [OneTimeTearDown]
    public void StopWireMock()
    {
        server?.Stop();
        server?.Dispose();
        client?.Dispose();
        factory.Dispose();
    }

    [Test]
    public async Task Test_GetNearbyUsers()
    {
        var response = await client.GetAsync("/users/nearby?lat=-37.3159&lng=81.1496&miles=1800");
        var content = Encoding.UTF8.GetString(await response.Content.ReadAsByteArrayAsync());
        var json = await response.Content.ReadFromJsonAsync<List<Dictionary<string, object>>>();
        var id = json?[0]["id"];
        Assert.That(id, Is.Not.Null);
        Assert.That(((JsonElement)id).GetInt32(), Is.EqualTo(1));
    }
}
