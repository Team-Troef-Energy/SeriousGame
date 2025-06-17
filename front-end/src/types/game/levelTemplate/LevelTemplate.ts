import { objective } from "./Objective";
import { resourceCosts } from "./ResourceCosts";
import season from "./Season";
import { templateType } from "./TemplateType";
import { TransformerConfiguration } from "./TransformerConfiguration";

export interface levelTemplate {
    id?: number;
    levelType?: templateType;
    raceId?: number;
    levelNumber: number;
    objective: objective;
    season: season;
    transformers: TransformerConfiguration[];
    cost: resourceCosts
    startTime: number;
    endTime: number;
}