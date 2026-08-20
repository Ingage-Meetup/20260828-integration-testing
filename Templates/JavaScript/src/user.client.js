export class JsonPlaceholderClient {
    /**
     * @param {string} baseUrl
     */
    constructor(baseUrl) {
        this.baseUrl = baseUrl.endsWith('/') ? baseUrl.slice(0, -1) : baseUrl;
    }

    /**
     * @param {string} path
     * @param {object|string} [queryParams]
     * @returns {Promise<string>} The response body as text
     */
    async getAsync(path, queryParams) {
        const cleanPath = path.startsWith('/') ? path : `/${path}`;
        const url = new URL(`${this.baseUrl}${cleanPath}`);

        if (queryParams) {
            const params = new URLSearchParams(queryParams);
            params.forEach((value, key) => url.searchParams.append(key, value));
        }

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return response.text();
    }
}
