import { StorageModalComponent } from './../storage-modal/storage-modal.component';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { LazyLoadEvent, PrimeNGConfig, MessageService } from 'primeng/api';
import { PageableSortableView } from 'src/app/model/PageableSort';
import { Storage } from 'src/app/model/Storage';

import { StorageDrink } from './../../model/StorageDrink';
import { StorageService } from './../../service/storage.service';

@Component({
  selector: 'app-storage',
  templateUrl: './storage.component.html',
  styleUrls: ['./storage.component.scss'],
})
export class StorageComponent implements OnInit {
  storages: Storage[];
  storageDrinks: StorageDrink[];
  pageNumber: number;
  fullStorage: boolean;
  keyword: string = '';
  idSelectedStorage: number;
  selectedStorage: Storage;
  @ViewChild(StorageModalComponent, { static: false })
  viewChildStorageModalComponent: StorageModalComponent;
  pageRequest: PageableSortableView<StorageDrink>;
  readonly directionAscending: string = 'asc';
  readonly directionDescending: string = 'desc';
  searchQuery: string = '';
  showModalPai: boolean = false;
  value6: any;

  constructor(
    private storageService: StorageService,
    private primengConfig: PrimeNGConfig,
    private messageService: MessageService
  ) {}

  async ngOnInit(): Promise<void> {
    this.primengConfig.ripple = true;
    this.getStorages();
  }

  private getStorages() {
    this.storageService.getStorages().subscribe({
      next: (response: any) => {
        this.storages = response;
        //console.log(this.storages[0]['id']); aceder primeiro elemento
      },
      error: () => console.log('erro'),
    });
  }

  openModalAndGetStorage(storage: Storage) {
    this.showModalPai = true;
    this.viewChildStorageModalComponent.selectedStorageModal = storage;
    this.viewChildStorageModalComponent.headerStorage =
      storage.location + ' - ' + storage.code;
    this.idSelectedStorage = storage.id;
    this.lazyLoad({ first: 0, sortOrder: 1, sortField: 'drink.name' });
  }

  getStorageId(id: number) {
    this.idSelectedStorage = id;
    //criar um LazyLoadEvent "ficticio" para a 1ª vez que passa no método e
    //Ainda não temos o evento que veio da tabela
    this.lazyLoad({ first: 0, sortOrder: 1, sortField: 'drink.name' });
  }

  //A lógica deste lazy muda pois tem [lazyLoadOnInit]="false" no html
  //para impedir que ao carregar a página na 1 vez chame  lazyLoad(event: LazyLoadEvent)
  lazyLoad(event: LazyLoadEvent) {
    event.first == 0
      ? (this.pageNumber = event.first)
      : (this.pageNumber = event.first / this.pageRequest.size);

    if (this.pageRequest) {
      event.sortOrder === 1
        ? (this.pageRequest.pageable.sort.direction = this.directionAscending)
        : (this.pageRequest.pageable.sort.direction = this.directionDescending);
      this.pageRequest.pageable.sort.property = event.sortField
        ? event.sortField
        : 'drink.name';
      this.pageRequest.pageable.pageNumber = this.pageNumber;
    }
    if (this.idSelectedStorage) {
      this.getStorageDrinks();
    }
  }

  onShowSucessMessageAndUpdatePai(sucess: boolean): void {
    console.log('evento enviado para o pai ', sucess);
    if (sucess) {
      this.showModalPai = false;
      this.getStorages();
      this.getStorageDrinks();
    }
  }

  captureEvent() {
    this.getStorageDrinks();
  }

  // onSuccesResultAndUpdate(sucess: boolean) {  }

  getStorageDrinks() {
    this.storageService
      .getStoragesDrink(this.pageRequest, this.keyword, this.idSelectedStorage)
      .subscribe({
        next: (response: PageableSortableView<StorageDrink>) => {
          this.pageRequest = response;
        },
        error: (httpError: HttpErrorResponse) => {
          this.createToastTable(
            'error',
            'Erro!',
            'Erro carregar dados dos armazens!'
          );
        },
      });
  }

  createToastTable(
    severity: string,
    summary: string,
    detailToast: string
  ): void {
    this.messageService.add({
      severity: severity,
      summary: summary,
      detail: detailToast,
    });
  }
}
