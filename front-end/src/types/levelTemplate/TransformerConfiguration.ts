import { congestion } from "./Congestion";
import { houseConfiguration } from "./HouseConfiguration";

export interface TransformerConfiguration {
    maxBatteryCount: number;
    congestion: congestion;
    houses: houseConfiguration[];
}