import {userGlobal} from "../context/AuthProvider.ts";

class HTTPRequestManager {
    private apiUrl: string | null = null;

    constructor() {
        this.loadConfig();
    }

    private async loadConfig() {
        this.apiUrl = {
            "localhost": 'http://localhost:8080',
            "dev.troefgame.jonaqhan.nl": "https://dev.troefgame.jonaqhan.nl/backend",
            "troefgame.duckdns.org": "https://troefgame.duckdns.org/backend",
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

        const headers: Record<string, string> = {};

        if (userGlobal?.value) {
            const idToken = await userGlobal.value.getIdToken();
            headers["Authorization"] = `Bearer ${idToken}`;
        }

        const isBodyPresent = !['GET', 'HEAD'].includes(method);
        if (isBodyPresent) {
            headers['Content-Type'] = 'application/json;charset=utf-8';
        }

        const response = await fetch(`${this.apiUrl}${path}`, {
            method: method,
            headers: headers,
            body: isBodyPresent ? JSON.stringify(body) : undefined
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`HTTP error! Status: ${response.status}, Response: ${errorText}`);
            const error = Object.assign(new Error(`HTTP error! Status: ${response.status}, Response: ${errorText}`), {
                status: response.status,
                body: errorText
            });

            throw error;
        }

        return hasJsonResponse ? response.json() : response;
    }
}

export const httpRequestManager = new HTTPRequestManager();