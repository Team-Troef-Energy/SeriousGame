import { resourceCosts } from "./ResourceCosts";
import season from "./Season";

export interface levelTemplate {
    levelNumber: number;
    maxCo2: number;
    maxCoins: number;
    season: season;
    amountOfBatteriesForTransformator: number;
    resourceCosts: resourceCosts
    startTime: number;
    endTime: number;
}