from pydantic.dataclasses import dataclass
from typing import Optional

@dataclass(frozen=True)
class Company:
    name: str
    catch_phrase: Optional[str] = None
    bs: Optional[str] = None

@dataclass(frozen=True)
class Geo:
    lat: float
    lng: float

@dataclass(frozen=True)
class Address:
    street: str
    suite: str
    city: str
    zipcode: str
    geo: Optional[Geo] = None

@dataclass(frozen=True)
class BaseUser:
    id: int
    name: str
    username: str
    email: str
    phone: str
    website: str
    company: Company

@dataclass(frozen=True)
class AddressUser(BaseUser):
    address: Address