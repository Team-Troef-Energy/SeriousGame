import { batteries } from './Batteries';
 export interface popupProperties {
     isOpen: boolean;
     title: string;
     type: string;
     energyProduction: number;
     energyConsumption: number;
     heatPump?: boolean;
     electricVehicle?: boolean;
     solarPanels?: number;
     batteries?: batteries;
     totalPowerCost?: number;
     solarPanelCost?: number;
 }