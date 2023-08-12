import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, PrimeNGConfig } from 'primeng/api';
import { Subject } from 'rxjs';
import { PageableSortableView } from 'src/app/model/PageableSort';
import { DrinkService } from 'src/app/service/drink.service';

import { Drink } from './../../model/Drink';
import { DrinkModalComponent } from './../drink-modal/drink-modal.component';

@Component({
  selector: 'app-drink',
  templateUrl: './drink.component.html',
  styleUrls: ['./drink.component.scss'],
})
export class DrinkComponent implements OnInit {

  keyword: string = '';
  keyToSearch: string = '';
  showModalNoComponentePai: boolean = false;
  selectedDrinkPai: Drink;
  pageNumber: number;
  loading: boolean;
  drinks: Drink[];
  errorText: string;
  pageRequest: PageableSortableView<Drink>;
  //https://upmostly.com/angular/what-does-the-static-property-on-viewchild-do
  modalStatePai$: Subject<boolean> = new Subject();

  @ViewChild(DrinkModalComponent, { static: false }) viewChildDrinkModalComponent: DrinkModalComponent;

  readonly editModal: string = 'editModal';
  readonly createModal: string = 'createModal';
  readonly directionAscending: string = 'asc';
  readonly directionDescending: string = 'desc';

  constructor(
    private service: DrinkService,
    private primengConfig: PrimeNGConfig,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loading = true;
    this.primengConfig.ripple = true;
  }

  public lazyLoad(event: LazyLoadEvent): void {
    this.pageNumber = event.first == 0 ? event.first : event.first / this.pageRequest.size;

    if (this.pageRequest) {
      this.pageRequest.pageable.sort.direction = event.sortOrder === 1  ? 'asc' : 'desc';
      this.pageRequest.pageable.sort.property = event.sortField  ? event.sortField : 'nome';
      this.pageRequest.pageable.pageNumber = this.pageNumber;
    }
    this.getDrinksComponent();
  }

  getDrinksComponent(): void {
    this.service.getDrinks(this.keyword, this.pageRequest).subscribe({
      next: (response: any) => {
        this.pageRequest = response;
      },
      error: (httpError: HttpErrorResponse) => {
        this.viewChildDrinkModalComponent.createToastTable(
          'error',
          'Erro!',
          httpError.statusText
        );
      },
      complete: () => (this.loading = false),
    });
  }

  setIsModalActionCreateAndOpenModal(value: boolean): void {
    this.showModalNoComponentePai = true;
    this.viewChildDrinkModalComponent.IsModalCreate = value;
    this.modalStatePai$.next(this.showModalNoComponentePai);
  }

  onRowUnselectOnDrinkTable() {
    if (this.selectedDrinkPai) {
      this.selectedDrinkPai = null;
    }
  }

  onShowSucessMessageAndUpdateNoPai(ShowToastAndUpdate: boolean): void {
    console.log("recebi no pai drink " , ShowToastAndUpdate);

    if (ShowToastAndUpdate) {
      this.showModalNoComponentePai = false;
      if (this.selectedDrinkPai) {
        this.selectedDrinkPai = null;
      }
      this.getDrinksComponent();
    }
  }

  openModalConfirmDialog(): void {
    this.confirmationService.confirm({
      message:
        'Tem a certeza que pretende apagar a bebida ' +
        this.selectedDrinkPai.name +
        ' da marca ' +
        this.selectedDrinkPai.nameBrand +
        '?',
      header: 'Confirmação',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.service.doSoftDeletDrinks(this.selectedDrinkPai.id).subscribe({
          error: (httpError: HttpErrorResponse) => {
            httpError.status == 409
            ? (this.errorText = httpError.error)
            : (this.errorText = 'Ocorreu um erro ao apagar a marca.');
            this.viewChildDrinkModalComponent.createToastTable('error', 'Erro!', this.errorText);
          },
          complete: () => {
            this.viewChildDrinkModalComponent.createToastTable(
              'success',
              'Dados excluidos!',
              'Bebida apagada com sucesso!'
            ),
              this.getDrinksComponent();
            if (this.selectedDrinkPai) {
              this.selectedDrinkPai = null;
            }
          },
        });
      },
      reject: () => {
        this.viewChildDrinkModalComponent.createToastTable(
          'warn',
          '',
          'Operação cancelada'
        );
      },
    });
  }

  captureEvent() {
    console.log("CLICK");

    this.getDrinksComponent();
  }

}
