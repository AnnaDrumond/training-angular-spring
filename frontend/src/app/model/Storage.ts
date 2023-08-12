import { StorageDrink } from "./StorageDrink";

export interface Storage {
  id: number;
  code: string;
  location: string;
  capacity: number;
  totalDrinksStored : number;
  remainingCapacity : number;
}
