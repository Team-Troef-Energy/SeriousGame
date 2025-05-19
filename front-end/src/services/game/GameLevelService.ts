
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

    fetchChatBotMessage = async (message: { [key: string]: any }) => {
        try {
          const response = await fetch("http://localhost:5000/python-data", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(message)
          });
      
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
      
          const data = await response.json();
          return data;
        } catch (error) {
          console.error("There was a problem with the fetch operation:", error);
          throw error;
        }
      }
}

export const gameLevelService = new GameLevelService();