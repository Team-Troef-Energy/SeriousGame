
import { httpRequestManager } from '../HTTPRequestManager';
const BASE_NAME = '/templates';

class TemplateLevelService {

    /**
     * Fetches all levels that exist.
     * @returns Promise<any>
     */
    fetchAllLevels = async () => {
        return await httpRequestManager.doFetch(BASE_NAME, 'GET');
    };
}

export const templateLevelService = new TemplateLevelService();