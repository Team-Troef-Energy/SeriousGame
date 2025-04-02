import { battery } from "./Battery";
import { congestion } from "./Congestion";
import { solarPanel } from "./SolarPanel";

export interface houseConfiguration {
    houseNumber: number;
    hasHeatPump: boolean;
    hasElectricVehicle: boolean;
    congestion: congestion;
    battery: battery,
    solarPanel: solarPanel
}