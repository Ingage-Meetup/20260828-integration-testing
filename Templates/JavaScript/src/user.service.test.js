import { test, beforeEach } from 'node:test';
import { strictEqual } from 'node:assert';
import { UserService } from './user.service.js';

// Sample JSON string mimicking the raw HTTP string data returned by the client
const sampleUsers = `[
	{
		"id": 1,
		"name": "Alice",
		"username": "alice1",
		"email": "alice@example.com",
		"address": {
			"street": "1 Main St",
			"suite": "Apt 1",
			"city": "Nowhere",
			"zipcode": "00000",
			"geo": { "lat": -37.3159, "lng": 81.1496 }
		}
	},
	{
		"id": 2,
		"name": "Bob",
		"username": "bob2",
		"email": "bob@example.com",
		"address": {
			"street": "2 Oak Ave",
			"suite": "",
			"city": "Somewhere",
			"zipcode": "11111",
			"geo": { "lat": -31.8129, "lng": 62.5342 }
		}
	},
	{
		"id": 3,
		"name": "Carol",
		"username": "carol3",
		"email": "carol@example.com",
		"address": {
			"street": "3 Pine Rd",
			"suite": "",
			"city": "Elsewhere",
			"zipcode": "22222",
			"geo": { "lat": -43.9509, "lng": -34.4618 }
		}
	}
]`;

let service;
let mockClient;

beforeEach(() => {
    // Mock client stub mirroring the getAsync method behavior
    mockClient = {
        getAsync: async (path) => {
            if (path === '/users') {
                return sampleUsers;
            }
            return '[]';
        }
    };

    service = new UserService(mockClient);
});

test('test_getNearbyUsers - should find Alice within radius', async () => {
    const result = await service.getNearbyAsync(-37.3159, 81.1496, 1800);

    // Array.prototype.some() mirrors LINQ's .Any() extension method
    const hasAlice = result.some(user => user.id === 1);

    strictEqual(hasAlice, true);
});