import {calculateDistance} from './distanceUtils.js';

/** @typedef {import('./user.model.js').User User} */
/** @typedef {import('./user.model.js').AddressUser AddressUser} */

export class UserService {
    /**
     * @param {JsonPlaceholderClient} jsonPlaceholderClient
     */
    constructor(jsonPlaceholderClient) {
        this.client = jsonPlaceholderClient;
    }

    /**
     * @returns {Promise<Array<User>>}
     */
    async getAllAsync() {
        const content = await this.client.getAsync('/users');
        if (!content) {
            return [];
        }

        return /** @type Array<User> */ (JSON.parse(content));
    }

    /**
     * @param {string|number} id
     * @returns {Promise<User|null>}
     */
    async getByIdAsync(id) {
        const content =  await this.client.getAsync('/users');
        if (!content) {
            return null;
        }

        const allUsers = /** @type Array<User> */ (JSON.parse(content));
        return allUsers.find(user => user.id == id) || null;
    }

    /**
     * @param {number} lat
     * @param {number} lng
     * @param {number} miles
     * @returns {Promise<Array<AddressUser>>} Users within the spatial radius
     */
    async getNearbyAsync(lat, lng, miles) {
        const allUsers = /** @type Array<AddressUser> */(await this.getAllAsync());

        return allUsers.filter(user => {
            const geo = user.address?.geo;
            if (!geo || geo.lat === undefined || geo.lng === undefined) {
                return false;
            }

            const distance = calculateDistance(lat, lng, Number(geo.lat).toFixed(3), Number(geo.lng).toFixed(3));
            return distance <= miles;
        });
    }
}
