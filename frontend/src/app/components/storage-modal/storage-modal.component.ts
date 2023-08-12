import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Drink } from 'src/app/model/Drink';
import { Storage } from 'src/app/model/Storage';
import { DrinkService } from 'src/app/service/drink.service';
import { StorageService } from 'src/app/service/storage.service';

import { StorageDrink } from './../../model/StorageDrink';

@Component({
  selector: 'app-storage-modal',
  templateUrl: './storage-modal.component.html',
  styleUrls: ['./storage-modal.component.scss'],
})
export class StorageModalComponent implements OnInit {
  quantity: any;
  selectedDrink: Drink;
  selectedStorageModal: Storage;
  headerStorage: string;
  drinks: Drink[];
  errorText: string;
  @Input() onShowModalFilhoRecebeDoPai: boolean;
  @Output() hideModalFilhoEnviaParaPai = new EventEmitter<boolean>();
  @Output() showSucessMessageAndUpdateFilho = new EventEmitter<boolean>();


  constructor(
    private storageService: StorageService,
    private drinkService: DrinkService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.drinkService.getDrinksSimple().subscribe({
      next: (response: Drink[]) => {
        this.drinks = response;
      },
      error: (httpError: HttpErrorResponse) => {
        this.messageService.add({
          key: 'toast-modal-storage',
          severity: 'error',
          summary: 'Erro!',
          detail: httpError.statusText,
        });
      },
    });
  }

  onAddDrinkInStorage(dataForm: NgForm) {
    if (!dataForm.valid) {
      this.createToastTable(
        'warn',
        'Atenção!',
        'Não podem haver campos vazios'
      );
    } else {
      let storageDrink = new StorageDrink();
      storageDrink.quantity = dataForm.value.quantity;
     storageDrink.idDrink = dataForm.value.drink.id;
      console.log( "dataform " , dataForm.value);
      storageDrink.idStorage = this.selectedStorageModal.id;
      if (storageDrink.quantity > this.selectedStorageModal.remainingCapacity) {
        this.createToastTable(
          'warn',
          '',
          'Não existe espaço suficiente neste armazem'
        );
      } else {
        console.log("storageDrink " , storageDrink);

        this.storageService.addDrinkInStorage(storageDrink).subscribe({
          next: () => {// o front não devolve nada
            dataForm.reset(),
              this.createToastTable(
                'success',
                'Dados guardados!',
                'Bebida adicionada com sucesso!'
              );
            this.showSucessMessageAndUpdateFilho.emit(true);
          },
          error: (httpError: HttpErrorResponse) => {
            dataForm.reset(),
              httpError.status == 409
                ? (this.errorText = httpError.error)
                : (this.errorText = 'Ocorreu um erro ao adicionar bebida.');
            this.createToastTable('error', 'Erro!', this.errorText);
          },
        });
      }
    }
  }

  cancelActionModal(dataForm: NgForm, showModal: boolean) {
    if (dataForm) {
      dataForm.reset();
    }
    this.hideModalFilhoEnviaParaPai.emit(showModal);
    this.createToastTable('warn', '', 'Operação cancelada');
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
