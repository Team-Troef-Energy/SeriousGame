import { createRaceDTO } from '../../types/dto/CreateRaceDTO';
import { httpRequestManager } from '../HTTPRequestManager';
const BASE_URL = '/races';

class RaceService {

    createRace = async (createRaceDTO: createRaceDTO) => {
        return await httpRequestManager.doFetch(`${BASE_URL}`, 'POST', createRaceDTO);
    };

    fetchRacesByEmail = async (email: string) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/by-email?email=${encodeURIComponent(email)}`);
    };


    fetchRaceById = async (id: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${id}`);
    };


    deleteRace = async (id: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${id}`, 'DELETE');
    };


    updateRaceName = async (id: number, name: string) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${id}/name?name=${encodeURIComponent(name)}`, 'PUT');
    };

    checkIfRaceBelongsToEmail = async (id: number, email: string) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${id}/is-email?email=${encodeURIComponent(email)}`);
    };
}

export const raceService = new RaceService();