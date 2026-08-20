using NUnit.Framework;

namespace TestProject;

public class DistanceUtilsTests
{
    [Test]
    public void Test_CalculateDistance()
    {
        double actual = DistanceUtils.CalculateDistance(-37.3159, 81.1496, -31.8129, 63.5342);
        Assert.That(actual, Is.EqualTo(1071.0980280170145d));
    }
}
