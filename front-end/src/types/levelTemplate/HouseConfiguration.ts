import { congestion } from "./Congestion";

export interface houseConfiguration {
    houseNumber: number;
    congestion: congestion;
    hasHeatPump: boolean;
    hasElectricVehicle: boolean;
    maxBatteries: number;
    maxSolarPanels: number;
}