import os
import httpx
from typing import Any, Dict, Optional, Union

class JsonPlaceholderClient:

    def __init__(self) -> None:
        """Initializes the client and cleans trailing slashes from the base URL."""
        self.base_url: str = os.environ.get("URL", "https://jsonplaceholder.typicode.com")

    async def get_async(
        self,
        path: str,
        query_params: Optional[Union[Dict[str, Any], str]] = None
    ) -> str:
        # Ensure path starts with a single forward slash
        clean_path = path if path.startswith("/") else f"/{path}"
        url = f"{self.base_url}{clean_path}"

        async with httpx.AsyncClient() as client:
            response = await client.get(url, params=query_params)
            response.raise_for_status()

            return response.text