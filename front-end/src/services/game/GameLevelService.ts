
import { httpRequestManager } from '../HTTPRequestManager';
const BASE_NAME = '/levels';

class GameLevelService {

    /**
     * Fetches the starting level by level number.
     * @param levelNumber - The level number to fetch.
     * @returns Promise<any>
     */
    fetchStartLevel = async (levelNumber: string | string[]) => {
        return await httpRequestManager.doFetch(`${BASE_NAME}/start/${levelNumber}`);
    };

    /**
     * Updates a certain level with the new game information.
     * @param levelNumber - The level number to update.
     * @param levelUpdateDto - The update data for the level.
     * @returns Promise<any>
     */
    fetchUpdateLevel = async (levelNumber: string | string[], levelUpdateDto: any) => {
        return await httpRequestManager.doFetch(`${BASE_NAME}/update/${levelNumber}`, 'POST', levelUpdateDto);
    };
}

export const gameLevelService = new GameLevelService();