import { objective } from "./Objective";
import { resourceCosts } from "./ResourceCosts";
import season from "./Season";
import { transformator } from "./Transformator";

export interface levelTemplate {
    levelNumber: number;
    objective: objective;
    season: season;
    transformators: transformator[];
    resourceCosts: resourceCosts
    startTime: number;
    endTime: number;
}