import { Drink } from "./Drink";

export interface Brand {
  id: number;
  source:string;
  name:string;
  numberOfDrinks: number;
  drinks : Drink[];
}
