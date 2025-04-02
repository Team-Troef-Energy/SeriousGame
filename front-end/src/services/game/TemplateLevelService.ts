
import { levelTemplate } from '../../types/levelTemplate/LevelTemplate';
import { httpRequestManager } from '../HTTPRequestManager';
const BASE_NAME = '/templates';

class TemplateLevelService {

    /**
     * Create a level template
     * @param levelTemplate 
     * @returns 
     */
    createLevelTemplate = async (levelTemplate: levelTemplate) => {
        return await httpRequestManager.doFetch(BASE_NAME, 'POST', levelTemplate);
    };

    /**
     * Update a level template
     * @param templateId 
     * @returns 
     */
    updateLevelTemplate = async (templateId:number, levelTemplate: levelTemplate) => {
        return await httpRequestManager.doFetch(`${BASE_NAME}/${templateId}`, 'PUT', levelTemplate);
    };

    /**
     * Delete a level template
     * @param templateId 
     * @returns 
     */	
    deleteLevelTemplate = async (templateId:number) => {
        return await httpRequestManager.doFetch(`${BASE_NAME}/${templateId}`, 'DELETE');
    };

    /**
     * Fetches all levels that exist.
     * @returns Promise<any>
     */
    fetchAllLevels = async () => {
        return await httpRequestManager.doFetch(BASE_NAME, 'GET');
    };
}

export const templateLevelService = new TemplateLevelService();