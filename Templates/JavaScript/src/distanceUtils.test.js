import { describe, it } from "node:test";
import assert from "node:assert";
import { calculateDistance } from "./distanceUtils.js";

describe("Distance Utils Tests", () => {

    it("should find the distance", () => {
        const actual = calculateDistance(-37.3159, 81.1496, -31.8129, 63.5342);
        assert.strictEqual(actual, 1069.911579640077);
    });
});