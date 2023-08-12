export interface Drink {
  id: number;
  name: string;
  unitPrice: number;
  volumeMl: number;
  type: TypeDrink;
  discontinued: boolean;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  nameBrand: string;
  idBrand: number;
 
}

export interface TypeDrink {
  drinkCode: number;
  typeValue: string;
}
