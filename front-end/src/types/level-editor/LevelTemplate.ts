import { HouseConfiguration } from "./HouseConfiguration";
import { objective } from "./Objective";
import { resourceCosts } from "./ResourceCosts";
import season from "./Season";

export interface levelTemplate {
    levelNumber: number;
    objective: objective;
    season: season;
    amountOfBatteriesForTransformator: number;
    houses: HouseConfiguration[];
    resourceCosts: resourceCosts
    startTime: number;
    endTime: number;
}