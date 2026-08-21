from pydantic import BaseModel, ConfigDict
from typing import Optional

class Company(BaseModel):
    name: str
    catch_phrase: Optional[str] = None
    bs: Optional[str] = None

class Geo(BaseModel):
    model_config = ConfigDict(strict=True)
    lat: float
    lng: float

class Address(BaseModel):
    street: str
    suite: str
    city: str
    zipcode: str
    geo: Optional[Geo] = None

class BaseUser(BaseModel):
    id: int
    name: str
    username: str
    email: str
    phone: str
    website: str
    company: Company

class AddressUser(BaseUser):
    address: Address