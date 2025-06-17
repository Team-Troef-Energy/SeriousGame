import { levelTemplate } from "../game/levelTemplate/LevelTemplate";

export interface race {
    id: number;
    name: string;
    userEmail: string;
    levels?: levelTemplate[];
  }