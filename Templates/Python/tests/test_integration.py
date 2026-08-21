import pytest
import httpx

def test_find_nearby_users():
    res = httpx.get("http://localhost:8000/users/nearby?lat=-37.3159&lng=81.1496&miles=1200")
    actual = res.json()
    assert actual[0]["id"] == 1