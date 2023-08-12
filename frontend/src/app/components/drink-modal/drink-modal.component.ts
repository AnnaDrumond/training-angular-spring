import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Subject } from 'rxjs';
import { Brand } from 'src/app/model/Brand';
import { Drink, TypeDrink } from 'src/app/model/Drink';
import { DrinkService } from 'src/app/service/drink.service';

import { BrandService } from './../../service/brand.service';

@Component({
  selector: 'app-drink-modal',
  templateUrl: './drink-modal.component.html',
  styleUrls: ['./drink-modal.component.scss'],
})
export class DrinkModalComponent implements OnInit {

  name: string;
  unitPrice: number;
  brands: Brand[];
  idBrand: number;
  isSaveButton: boolean;
  typesDrink: TypeDrink[];
  IsModalCreate: boolean;
  drinkToEdit: Drink;
  textToastSucess: string;

  @Input() onShowModalFilhoRecebeValorDoPai: boolean;
  @Input() selectedDrinkFilho: Drink;
  @Input() modalStateFilho$: Subject<boolean>;

  @Output() hideModalFilhoEnviaValorParaPai = new EventEmitter<boolean>();
  @Output() showSucessMessageAndUpdateNoFilho = new EventEmitter<boolean>();

  readonly editText: string = 'Editar bebida';
  readonly createText: string = 'Adicionar nova bebida';

  constructor(
    private brandService: BrandService,
    private drinkService: DrinkService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.modalStateFilho$?.subscribe(() => {
      this.getTypesDrinkComponent();
      this.getBrandsComponent();
      if (!this.IsModalCreate && this.selectedDrinkFilho) {
        this.drinkToEdit = { ...this.selectedDrinkFilho };//shallow copy
        //OR this.drinkToEdit = Object.assign({}, this.selectedDrink);
      }
    });
  }

  onAddDrink(dataDrinkForm: NgForm) {
    if (!dataDrinkForm.valid) {
      this.createToastModal('Não podem existir campos vazios!');
    } else {
      this.drinkService.createDrink(dataDrinkForm.value).subscribe({
        next: (response: Drink) => {
          dataDrinkForm.reset(),
            this.createToastTable(
              'success',
              'Dados guardados!',
              'Bebida criada com sucesso!'
            );
          if (response) {
            console.log("Drink - vou emitir");

            this.showSucessMessageAndUpdateNoFilho.emit(true);
          }
        },
        error: () => this.createToastModal('Erro ao criar nova bebida!'),
      });
    }
  }

  cancelActionModal(dataDrinkForm: NgForm, showModal: boolean): void {
    if (dataDrinkForm) {
      dataDrinkForm.reset();
    }
    this.hideModalFilhoEnviaValorParaPai.emit(showModal);
    this.createToastTable('warn', '', 'Operação cancelada');
  }

  onEditDrink(dataDrinkForm: NgForm) {
    if (!dataDrinkForm.valid) {
      this.createToastModal('Não podem existir campos vazios!');
    } else {
      this.drinkService
        .editDrink(dataDrinkForm.value, this.selectedDrinkFilho.id)
        .subscribe({
          next: (response: Drink) => {
            if (response) {
              this.showSucessMessageAndUpdateNoFilho.emit(true);
            }
          },
          error: () => this.createToastModal('Erro ao editar bebidas!'),
          complete: () => {
            dataDrinkForm.reset(),
              this.createToastTable(
                'success',
                'Dados alterados!',
                'Bebida editada com sucesso!'
              );
          },
        });
    }
  }

  getTypesDrinkComponent() {
    this.drinkService.getTypesDrink().subscribe({
      next: (response) => {
        this.typesDrink = response;
      },
      error: (httpError: HttpErrorResponse) =>
        this.createToastModal(httpError.statusText),
    });
  }

  getBrandsComponent() {
    this.brandService.getBrands().subscribe({
      next: (response: any) => {
        this.brands = response;
      },
      error: (httpError: HttpErrorResponse) =>
        this.createToastModal(httpError.statusText),
    });
  }

  createToastModal(detailToast: string): void {
    this.messageService.add({
      key: 'toast-modal',
      severity: 'error',
      summary: 'Erro!',
      detail: detailToast,
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
