export interface house {
  id: number;
  totalPowerCost: number;
  consumption: number;
  production: number;
  current: current;
  batteries: {
    totalCharge: number;
    amount: number;
    currentCharge: number;
  };
  solarpanels: number;
  hasCongestion: boolean;
  maxCurrent: number;
  hasElectricVehicle: boolean;
  hasHeatpump: boolean;
}

export interface transformer {
  id: number;
  batteries: { amount: number; totalCharge: number };
  houses: house[];
  current: current;
}

export interface hour {
  transformers: transformer[];
}

export interface levelData {
  season: string;
  endTime: number;
  startTime: number;
  hours: hour[];
}

export interface current {
  amount: number;
  direction: string;
}
