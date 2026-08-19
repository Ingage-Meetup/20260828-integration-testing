using IntegrationTesting;
using Moq;
using NUnit.Framework;

namespace TestProject;

public class UserServiceTests
{
    private const string SampleUsers = """
    [
        {
            "id": 1,
            "name": "Alice",
            "username": "alice1",
            "email": "alice@example.com",
            "address": {
                "street": "1 Main St",
                "suite": "Apt 1",
                "city": "Nowhere",
                "zipcode": "00000",
                "geo": {
                    "lat": "-37.3159",
                    "lng": "81.1496"
                }
            },
            "phone": "555-0101",
            "website": "alice.example.com",
            "company": {
                "name": "Alice Co",
                "catchPhrase": "Doing things",
                "bs": "synergize"
            }
        },
        {
            "id": 2,
            "name": "Bob",
            "username": "bob2",
            "email": "bob@example.com",
            "address": {
                "street": "2 Oak Ave",
                "suite": "",
                "city": "Somewhere",
                "zipcode": "11111",
                "geo": {
                    "lat": "-31.8129",
                    "lng": "62.5342"
                }
            },
            "phone": "555-0102",
            "website": "bob.example.com",
            "company": {
                "name": "Bob Inc",
                "catchPhrase": "Making stuff",
                "bs": "leverage"
            }
        },
        {
            "id": 3,
            "name": "Carol",
            "username": "carol3",
            "email": "carol@example.com",
            "address": {
                "street": "3 Pine Rd",
                "suite": "",
                "city": "Elsewhere",
                "zipcode": "22222",
                "geo": {
                    "lat": "-43.9509",
                    "lng": "-34.4618"
                }
            },
            "phone": "555-0103",
            "website": "carol.example.com",
            "company": {
                "name": "Carol LLC",
                "catchPhrase": "Getting it done",
                "bs": "disrupt"
            }
        }
    ]
    """;

    private UserService service;
    private Mock<IJsonPlaceholderClient> client;

    [SetUp]
    public void SetUp()
    {
        client = new Mock<IJsonPlaceholderClient>();
        client.Setup(x => x.GetAsync("/users", null)).ReturnsAsync(SampleUsers);
        service = new UserService(client.Object);
    }

    [Test]
    public async Task Test_GetNearbyUsers()
    {
        var result = await service.GetNearbyAsync(-37.3159, 81.1496, 1800);
        Assert.That(result.Find(x => x.Id == 1), Is.Not.Null);
    }
}
