namespace IntegrationTesting;

public record BaseUser(int Id, string Name, string Username, string Email, string Phone, string Website, Company Company);

public record Address(string Street, string Suite, string City, string Zipcode, Geo? Geo);

public record Company(string Name, string CatchPhrase, string Bs);

public record AddressUser(int Id, string Name, string Username, string Email, Address Address, string Phone, string Website, Company Company)
        : BaseUser(Id, Name, Username, Email, Phone, Website, Company);

public record Geo(double Lat, double Lng);