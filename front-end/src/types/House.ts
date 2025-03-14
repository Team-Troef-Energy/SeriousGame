import { batteries } from "./Batteries";
import { current } from "./Current";
export interface house {
  maxBatteryCount: number;
  maxSolarPanelCount: number;
  id: number;
  totalPowerCost: number;
  consumption: number;
  production: number;
  current: current;
  batteries: batteries;
  solarpanels: number;
  hasCongestion: boolean;
  maxCurrent: number;
  hasElectricVehicle: boolean;
  hasHeatpump: boolean;
}
