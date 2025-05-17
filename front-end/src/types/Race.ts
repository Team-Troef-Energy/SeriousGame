import { levelTemplate } from "./levelTemplate/LevelTemplate";

export interface race {
    id: number;
    name: string;
    userEmail: string;
    levels?: levelTemplate[];
  }