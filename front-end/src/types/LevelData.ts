import { hour } from "./Hour";
export interface levelData {
    totalCO2: number;
    objective: { maxCoins: number; maxCO2: number };
    totalCosts: number;
    season: string;
    endTime: number;
    startTime: number;
    hours: hour[];
  }