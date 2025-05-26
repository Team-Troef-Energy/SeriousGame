class HTTPRequestManager {
    private apiUrl: string | null = null;

    constructor() {
        this.loadConfig();
    }

    private async loadConfig() {
        this.apiUrl = {
            "localhost": 'http://localhost:8080',
            "dev.troefgame.jonaqhan.nl": "https://dev.troefgame.jonaqhan.nl/backend",
            "troefgame.duckdns.org": "http://troefgame.duckdns.org/backend",
        }[window.location.hostname] || null; // Hostname does not include port

        // Doesn't work, kept for future reference
        /*
        try {
            // Misschien klopt deze URL niet
            const response = await fetch('../../docker_profiles/config.json');
            if (!response.ok) {
                throw new Error('Failed to load config.json');
            }
            const config = await response.json();
            this.apiUrl = config.apiUrl;
        } catch (error) {
            console.error('Error loading config:', error);

            this.apiUrl = 'http://localhost:8080';
        }*/
    }

    async doFetch(path: string, method = 'GET', body: any = undefined, hasJsonResponse = true) {

        if (!this.apiUrl) {
            await this.loadConfig();
        }

        const isBodyPresent = !['GET', 'HEAD'].includes(method);

        const response = await fetch(`${this.apiUrl}${path}`, {
            method: method,
            headers: isBodyPresent ? { 'Content-Type': 'application/json;charset=utf-8' } : undefined,
            body: isBodyPresent ? JSON.stringify(body) : undefined
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`HTTP error! Status: ${response.status}, Response: ${errorText}`);
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return hasJsonResponse ? response.json() : response;
    }
}

export const httpRequestManager = new HTTPRequestManager();