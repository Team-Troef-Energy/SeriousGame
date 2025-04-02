import { congestion } from "./Congestion";
import { houseConfiguration } from "./HouseConfiguration";

export interface TransformerConfiguration {
    amountOfBatteries: number;
    maxBatteryCount: number;
    congestion: congestion;
    houses: houseConfiguration[];
}