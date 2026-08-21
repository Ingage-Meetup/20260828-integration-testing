from fastapi import FastAPI, Depends
from typing import Annotated

from app.service import UserService

app = FastAPI()

@app.get('/users')
async def get_users(service: Annotated[UserService, Depends()]):
    return await service.get_all_async()

@app.get('/user/{id}')
async def get_user(id: int, service: Annotated[UserService, Depends()]):
    return await service.get_by_id_async(id)

@app.get('/users/nearby')
async def get_users_neraby(service: Annotated[UserService, Depends()], lat: float, lng: float, miles: float | None = None):
    return await service.get_nearby_async()