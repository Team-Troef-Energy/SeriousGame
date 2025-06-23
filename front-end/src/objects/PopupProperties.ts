import { batteries, house, popupProperties, transformer } from "../types/game";

export class PopupProperties implements popupProperties {
    isOpen: boolean;
    title: string;
    type: string;
    energyProduction: number;
    energyConsumption: number;
    heatPump?: boolean;
    electricVehicle?: boolean;
    solarPanels?: number;
    batteries: batteries;
    totalPowerCost?: number;
    solarPanelCost?: number;

    constructor(properties: house | transformer, solarPanelCost: number, batteryCost: number) {
        if ((properties as house).hasHeatpump !== undefined) {
            this.title = `Huis ${properties.id}`;
            this.type = "huis";
            this.energyProduction = (properties as house).production;
            this.energyConsumption = (properties as house).consumption;
            this.heatPump = (properties as house).hasHeatpump;
            this.electricVehicle = (properties as house).hasElectricVehicle;
            this.solarPanels = (properties as house).solarpanels;
            this.batteries = (properties as house).batteries;
            this.totalPowerCost = (properties as house).totalPowerCost;
        } else {
            this.title = `Transformator ${properties.id}`;
            this.type = "transformator";
            this.energyProduction = (properties as transformer).current.direction === "PRODUCTION" ? (properties as transformer).current.amount : 0;
            this.energyConsumption = (properties as transformer).current.direction === "DEMAND" ? (properties as transformer).current.amount : 0
            this.batteries = (properties as transformer).batteries;
        }
        this.solarPanelCost = solarPanelCost;
        this.batteries.cost = batteryCost;
        this.isOpen = true; // Default value
    }
}