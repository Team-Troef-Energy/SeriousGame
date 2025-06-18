import { current } from "./Current";
import { house } from "./House";
export interface transformer {
    maxBatteryCount: number;
    id: number;
    batteries: { amount: number; totalCharge: number };
    houses: house[];
    current: current;
  }