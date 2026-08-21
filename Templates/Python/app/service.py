from typing import Annotated, Optional
from fastapi import Depends
from app.client import JsonPlaceholderClient
from app.models import BaseUser, AddressUser
from app.distance_utils import calculateDistance
import json

class UserService:
    
    def __init__(self, client: Annotated[JsonPlaceholderClient, Depends()]) -> None:
        self.client: JsonPlaceholderClient = client
    
    async def get_all_async(self) -> list[BaseUser]:
        content = await self.client.get_async("/users")
        data = json.loads(content)
        users = [BaseUser(**item) for item in data]
        return users
    
    async def get_by_id_async(self, id: str) -> Optional[BaseUser]:
        content = await self.client.get_async("/users")
        data = json.loads(content)
        users = [BaseUser(**item) for item in data]
        return next((u for u in users if u.id == id), None)
    
    async def get_nearby_async(self, lat: float, lng: float, miles: float) -> list[BaseUser]:
        content = await self.client.get_async("/users")
        data = json.loads(content)
        users = [AddressUser(**item) for item in data]
        return [u for u in users if calculateDistance(lat, lng, u.address.geo.lat, u.address.geo.lng) <= miles]