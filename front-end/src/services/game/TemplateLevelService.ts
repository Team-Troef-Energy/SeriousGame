
import { levelTemplate } from '../../types/levelTemplate/LevelTemplate';
import { httpRequestManager } from '../HTTPRequestManager';
const BASE_URL = '/templates';

class TemplateLevelService {

    /**
     * Create a level template
     * @param levelTemplate 
     * @returns 
     */
    createLevelTemplate = async (levelTemplate: levelTemplate) => {
        return await httpRequestManager.doFetch(BASE_URL, 'POST', levelTemplate);
    };

    /**
     * Update a level template
     * @param templateId 
     * @returns 
     */
    updateLevelTemplate = async (templateId:number, levelTemplate: levelTemplate) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${templateId}`, 'POST', levelTemplate);
    };

    /**
     * Delete a level template
     * @param templateId 
     * @returns 
     */	
    deleteLevelTemplate = async (templateId:number) => {
        return await httpRequestManager.doFetch(`${BASE_URL}/${templateId}`, 'DELETE', undefined, false);
    };

    /**
     * Fetches all levels that exist.
     * @returns Promise<any>
     */
    fetchAllLevels = async () => {
        return await httpRequestManager.doFetch(BASE_URL, 'GET');
    };

    fetchPromptMessage = async (message: { [key: string]: any }) => {
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

export const templateLevelService = new TemplateLevelService();