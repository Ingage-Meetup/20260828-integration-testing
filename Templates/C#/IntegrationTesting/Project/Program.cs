using IntegrationTesting;
using Microsoft.Extensions.Options;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddOpenApi();

builder.Services.Configure<HttpClientSettings>(builder.Configuration.GetSection("HttpClientSettings"));
builder.Services.AddHttpClient("MyApiClient", (sp, client) =>
{
    var settings = sp.GetRequiredService<IOptions<HttpClientSettings>>().Value;
    client.BaseAddress = new Uri(settings.BaseUrl);
});

builder.Services.AddSingleton<IJsonPlaceholderClient, JsonPlaceholderClient>();
builder.Services.AddSingleton<IUserService, UserService>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

var userService = app.Services.GetRequiredService<IUserService>();

app.MapGet("/users", async () =>
{
    var result = await userService.GetAllAsync();
    return result;
}).WithName("GetAllUsers");

app.MapGet("/user/{id}", async (string id) =>
{
    var result = await userService.GetByIdAsync(id);
    return result;
}).WithName("GetUser");

app.MapGet("/users/nearby", async (double lat, double lng, double miles) =>
{
    var result = await userService.GetNearbyAsync(lat, lng, miles);
    return result;
}).WithName("GetUsersNearby");

app.Run();
