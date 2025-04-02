let HOST = location.hostname === "localhost" ? "http://localhost:8080" : "https://troefgame.duckdns.org:5001";
class HTTPRequestManager {

    async doFetch(path: string, method = 'GET', body = undefined, hasJsonResponse = true) {
        let isBodyPresent = !["GET", "HEAD"].includes(method)

        const response = await fetch(`${HOST}${path}`, {
            method: method,
            headers: isBodyPresent ? { 'Content-Type': 'application/json;charset=utf-8' } : undefined,
            body: isBodyPresent ? JSON.stringify(body) : undefined
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return hasJsonResponse ? response.json() : response;
    }
}

export const httpRequestManager = new HTTPRequestManager()