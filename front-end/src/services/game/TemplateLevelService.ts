
import { levelTemplate } from '../../types/game/levelTemplate/LevelTemplate';
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
}

export const templateLevelService = new TemplateLevelService();