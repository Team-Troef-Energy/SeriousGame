
import { httpRequestManager } from '../HTTPRequestManager';
const BASE_URL = '/levels';

class GameLevelService {

    /**
     * Fetches the starting level by level number.
     * @param levelId - The level id to fetch.
     * @returns Promise<any>
     */
    fetchStartLevel = async (levelId: number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/start/${levelId}`);
    };

    /**
     * Updates a certain level with the new game information.
     * @param levelId - The level id to update.
     * @param levelUpdateDto - The update data for the level.
     * @returns Promise<any>
     */
    fetchUpdateLevel = async (levelId: number, levelUpdateDto: any) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/update/${levelId}`, 'POST', levelUpdateDto);
    };
}

export const gameLevelService = new GameLevelService();