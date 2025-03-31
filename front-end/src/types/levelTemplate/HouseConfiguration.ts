import { battery } from "./Battery";
import { solarPanel } from "./SolarPanel";

export interface houseConfiguration {
    houseNumber: number;
    hasHeatPump: boolean;
    hasElectricalVehicle: boolean;
    hasCongestion: boolean;
    battery: battery,
    solarPanel: solarPanel
}