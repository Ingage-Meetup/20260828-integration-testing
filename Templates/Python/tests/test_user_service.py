import unittest
import json
from unittest.mock import AsyncMock
from app.service import UserService


class TestUserService(unittest.IsolatedAsyncioTestCase):
    async def asyncSetUp(self):
        self.client = AsyncMock()
        self.client.get_async.return_value = """[
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
            ]"""
        self.service = UserService(self.client)
        
    async def test_getNearby(self):
        actual = await self.service.get_nearby_async(-37.3159, 81.1496, 1800)
        self.assertTrue(any(x.id == 1 for x in actual))


if __name__ == "__main__":
    unittest.main()
