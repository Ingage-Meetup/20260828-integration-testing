from importlib.resources import files
from pathlib import Path
import os
import subprocess
import time
import httpx
import pytest
from wiremock.server.server import WireMockServer

JAR = str(files("wiremock") / "server" / "wiremock-standalone-2.35.1.jar")
WIREMOCK_DIR = Path(__file__).parent.parent / "wiremock"
WIREMOCK_PORT = 9080

def _wait_for(url: str, timeout: int = 15):
    deadline = time.time() + timeout;
    while time.time() < deadline:
        try:
            httpx.get(url, timeout=1)
            return
        except Exception:
            time.sleep(0.25)
    
    raise TimeoutError(f"Server never became ready at {url}")


@pytest.fixture(scope="session", autouse=True)
def servers():
    wiremock = subprocess.Popen(
        [
            "java", "-jar", JAR,
            "--port", str(WIREMOCK_PORT),
            "--root-dir", str(WIREMOCK_DIR),
            "--proxy-all", "https://jsonplaceholder.typicode.com"
        ]
    )
    app = subprocess.Popen(
        ["uvicorn", "app.main:app", "--port", "8000"],
        env={**os.environ, "URL": f"http://localhost:{WIREMOCK_PORT}"},
    )
    try:
        _wait_for(f"http://localhost:{WIREMOCK_PORT}/__admin")
        _wait_for("http://localhost:8000/")
        yield
    finally:
        app.terminate()
        wiremock.terminate()
        app.wait()
        wiremock.wait()