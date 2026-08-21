import express, { json } from 'express';
import {JsonPlaceholderClient} from './user.client.js';
import {UserService} from './user.service.js';

const app = express();
const PORT = process.env.PORT || 8080;

const client = new JsonPlaceholderClient(process.env.URL);
const userService = new UserService(client);

app.use(json());

app.get('/', async (req, res) => {
    res.json({});
});

app.get('/users', async (req, res) => {
    try {
        const result = await userService.getAllAsync();
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.get('/user/:id', async (req, res) => {
    try {
        const result = await userService.getByIdAsync(req.params.id);

        if (!result) {
            return res.status(404).json({ message: 'User not found' });
        }

        res.json(result);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.get('/users/nearby', async (req, res) => {
    try {
        const lat = parseFloat(req.query.lat);
        const lng = parseFloat(req.query.lng);
        const miles = parseFloat(req.query.miles);

        if (isNaN(lat) || isNaN(lng) || isNaN(miles)) {
            return res.status(400).json({ error: 'Missing or invalid lat, lng, or miles query parameters' });
        }

        const result = await userService.getNearbyAsync(lat, lng, miles);
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});