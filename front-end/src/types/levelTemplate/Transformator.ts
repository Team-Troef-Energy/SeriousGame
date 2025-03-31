import { houseConfiguration } from "./HouseConfiguration";

export interface transformator {
    amountOfBatteries: number;
    maxBatteryCount: number;
    houses: houseConfiguration[];
}