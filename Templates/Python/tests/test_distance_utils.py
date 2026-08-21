import unittest
from app.distance_utils import calculateDistance


class TestDistanceUtils(unittest.TestCase):
    def test_calculateDistance(self):
        actual = calculateDistance(-37.3159, 81.1496, -31.8129, 63.5342)
        self.assertAlmostEqual(actual, 1069.9115796400774)


if __name__ == "__main__":
    unittest.main()
