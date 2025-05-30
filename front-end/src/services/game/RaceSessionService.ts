import { raceSession } from '../../types/RaceSession';

class RaceSessionService {
    private readonly SESSION_DATA_KEY = 'raceSessionData';

    private getSessionData(): raceSession | null {
        const session = sessionStorage.getItem(this.SESSION_DATA_KEY);
        if (!session) return null;
        try {
            const parsedSession = JSON.parse(session);
            if (parsedSession.code && parsedSession.username) {
                return parsedSession as raceSession;
            }
            return null;
        } catch {
            return null;
        }
    }

    setSession(session: raceSession) {
        sessionStorage.setItem(this.SESSION_DATA_KEY, JSON.stringify(session));
    }

    getSession(): raceSession | null {
        return this.getSessionData();
    }

    hasSession(): boolean {
        const session = this.getSessionData();
        return !!(session?.code && session?.username);
    }

    clearSession() {
        sessionStorage.removeItem(this.SESSION_DATA_KEY);
    }
}

export const raceSessionService = new RaceSessionService();