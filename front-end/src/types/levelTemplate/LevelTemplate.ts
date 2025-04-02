import { objective } from "./Objective";
import { resourceCosts } from "./ResourceCosts";
import season from "./Season";
import { TransformerConfiguration } from "./TransformerConfiguration";

export interface levelTemplate {
    id?: number;
    levelNumber: number;
    objective: objective;
    season: season;
    transformers: TransformerConfiguration[];
    cost: resourceCosts
    startTime: number;
    endTime: number;
}