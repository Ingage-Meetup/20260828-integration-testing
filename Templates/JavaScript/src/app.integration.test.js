import { describe, it } from "node:test";
import assert from "node:assert";

describe("Integration Tests", () => {

    it("should find the distance", async () => {
        const res = await fetch("http://localhost:8080/users/nearby?lat=-37.3159&lng=81.1496&miles=1200");
        const actual = await res.json();
        assert.strictEqual(actual[0].id, 1);
    });
});