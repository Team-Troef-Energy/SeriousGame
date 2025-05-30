import { raceSessionUser } from '../../types/race/RaceSessionUser';

class RaceSessionStorageService {
    private readonly SESSION_DATA_KEY = 'raceSession';

    private getSessionData(): raceSessionUser | null {
        const session = sessionStorage.getItem(this.SESSION_DATA_KEY);
        if (!session) return null;
        try {
            const parsedSession = JSON.parse(session);
            if (parsedSession.joinCode && parsedSession.username && parsedSession.token) {
                return parsedSession as raceSessionUser;
            }
            return null;
        } catch {
            return null;
        }
    }

    setSession(session: raceSessionUser) {
        sessionStorage.setItem(this.SESSION_DATA_KEY, JSON.stringify(session));
    }

    getSession(): raceSessionUser | null {
        return this.getSessionData();
    }

    hasSession(): boolean {
        const session = this.getSessionData();
        return !!(session?.joinCode && session?.username && session?.token);
    }

    clearSession() {
        sessionStorage.removeItem(this.SESSION_DATA_KEY);
    }
}

export const raceSessionStorageService = new RaceSessionStorageService();