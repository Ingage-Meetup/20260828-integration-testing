import httpx
from typing import Any, Dict, Optional, Union

class JsonPlaceholderClient:
    """A client for interacting with JSONPlaceholder using async HTTP requests."""

    def __init__(self) -> None:
        """Initializes the client and cleans trailing slashes from the base URL."""
        self.base_url: str = 'https://jsonplaceholder.typicode.com'

    async def get_async(
        self,
        path: str,
        query_params: Optional[Union[Dict[str, Any], str]] = None
    ) -> str:
        """Sends an async GET request and returns the response body as text."""
        # Ensure path starts with a single forward slash
        clean_path = path if path.startswith("/") else f"/{path}"
        url = f"{self.base_url}{clean_path}"

        async with httpx.AsyncClient() as client:
            # httpx automatically handles dictionary and string query params
            response = await client.get(url, params=query_params)

            # Raises an httpx.HTTPStatusError if the response was an error
            response.raise_for_status()

            return response.text