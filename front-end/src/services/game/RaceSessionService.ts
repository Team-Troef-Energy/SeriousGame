import { raceSessionJoin } from '../../types/race/RaceSessionJoin';
import { httpRequestManager } from '../HTTPRequestManager';

const BASE_URL = '/racesession';

class RaceSessionService {
    createSession = async (raceId: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}?raceId=${raceId}`, 'POST');
    };

    fetchSessionById = async (sessionId: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${sessionId}`);
    };

    fetchSessionByJoinCode = async (joinCode: string) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/by-joincode?joincode=${encodeURIComponent(joinCode)}`);
    };

    deleteSession = async (sessionId: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${sessionId}`, 'DELETE', null, false);
    };

    checkIfSessionCorrelatesWithRace = async (sessionId: number, raceId: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${sessionId}/checkRaceId/${raceId}`);
    };

    joinSession = async (raceSessionJoin: raceSessionJoin) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/join`, 'POST', raceSessionJoin);
    };

    leaveSession = async (token: string) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/leave?token=${encodeURIComponent(token)}`, 'POST', null, false);
    };
}

export const raceSessionService = new RaceSessionService();