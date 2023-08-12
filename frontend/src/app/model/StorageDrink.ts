export class StorageDrink {
  quantity: number;
  brandName: string;
  drinkVolume: number;
  drinkName: string;
  idDrink: number;
  idStorage: number;
  constructor();
  constructor(quantity: number, brandName: string, drinkVolume: number, drinkName: string,
    idDrink: number,idStorage: number );
  constructor(quantity?: number, brandName?: string, drinkVolume?: number, drinkName?: string,
    idDrink?: number,idStorage?: number) {
      this.quantity = quantity;
      this.brandName = brandName;
      this.drinkVolume = drinkVolume;
      this.drinkName = drinkName;
      this.idDrink = idDrink;
      this.idStorage = idStorage;
  }
}

/*export interface StorageDrink {
  quantity: number;
  brandName: string;
  drinkVolume: number;
  drinkName: string;
  idDrink: number;
  idStorage: number;
}*/



/*
export class AnexoForm {
    tipoDocumento: TipoDocumento;
    descricao: string;
    ficheiro: File;
    constructor();
    constructor(tipoDocumento: TipoDocumento, descricao: string, ficheiro: File);
    constructor(tipoDocumento?: TipoDocumento, descricao?: string, ficheiro?: File) {
        this.tipoDocumento = tipoDocumento;
        this.descricao = descricao;
        this.ficheiro = ficheiro;
    }
}
*/
