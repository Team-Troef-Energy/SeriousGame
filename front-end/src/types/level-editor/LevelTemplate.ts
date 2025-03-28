import { houseConfiguration } from "./HouseConfiguration";
import { objective } from "./Objective";
import { resourceCosts } from "./ResourceCosts";
import season from "./Season";
import { transformator } from "./Transformator";

export interface levelTemplate {
    levelNumber: number;
    objective: objective;
    season: season;
    transformator: transformator;
    houses: houseConfiguration[];
    resourceCosts: resourceCosts
    startTime: number;
    endTime: number;
}