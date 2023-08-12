import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import {
  ConfirmationService,
  LazyLoadEvent,
  MessageService,
  PrimeNGConfig,
} from 'primeng/api';
import { Brand } from 'src/app/model/Brand';

import { PageableSortableView } from 'src/app/model/PageableSort';
import { BrandService } from 'src/app/service/brand.service';
import {
  trigger,
  state,
  style,
  transition,
  animate,
} from '@angular/animations';
import { DrinkService } from 'src/app/service/drink.service';
import { Drink } from 'src/app/model/Drink';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrls: ['./brand.component.scss'],
  //https://www.primefaces.org/primeng/table/rowexpansion
  animations: [
    trigger('rowExpansionTrigger', [
      state(
        'void',
        style({
          transform: 'translateX(-10%)',
          opacity: 0,
        })
      ),
      state(
        'active',
        style({
          transform: 'translateX(0)',
          opacity: 1,
        })
      ),
      transition('* <=> *', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
    ]),
  ],
})
export class BrandComponent {

  loading: boolean;
  brands: Brand[];
  drinks: Drink[];
  brandId: number;
  selectedBrand: Brand;
  pageNumber: number;
  errorText: string;
  keyword: string = '';


  showModalNoComponentePai: boolean = false;


  
  pageRequest: PageableSortableView<Brand>;
  readonly directionAscending: string = 'asc';
  readonly directionDescending: string = 'desc';
  searchQuery: string = '';

  constructor(
    private brandService: BrandService,
    private drinkService: DrinkService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private primengConfig: PrimeNGConfig
  ) {}

  ngOnInit() {
    this.loading = true;
    this.primengConfig.ripple = true;
  }

  lazyLoad(event: LazyLoadEvent) {
    console.log("lazyLoad");
    console.log("this.pageRequest = " + this.pageRequest);
    event.first == 0
      ? (this.pageNumber = event.first)
      : (this.pageNumber = event.first / this.pageRequest.size);

    if (this.pageRequest) {
      event.sortOrder === 1
        ? (this.pageRequest.pageable.sort.direction = this.directionAscending)
        : (this.pageRequest.pageable.sort.direction = this.directionDescending);
      this.pageRequest.pageable.sort.property = event.sortField
        ? event.sortField
        : 'name';
      this.pageRequest.pageable.pageNumber = this.pageNumber;
    }

    this.getBrandsComponent();
  }

  captureEvent() {
    console.log(this.keyword);
    this.getBrandsComponent();
  }


  getBrandsComponent(): void {
    console.log("getBrandsComponent this.pageRequest " , this.pageRequest);

    this.brandService
      .getBrandsWithPaginatorAndSort(this.pageRequest, this.keyword)
      .subscribe({
        next: (response: PageableSortableView<Brand>) => {
          this.pageRequest = response;
        },
        error: (httpError: HttpErrorResponse) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro!',
            detail: httpError.statusText,//FIXME:pegar msg de erro enviada pelo backend
          });
        },
        complete: () => (this.loading = false),
      });
  }

  openModalCreate() {
    this.showModalNoComponentePai = true;
  }

  onRowUnselectOnBrandTable() {
    if (this.selectedBrand) {
      this.selectedBrand = null;
    }
  }

  //Recebo a brand toda - NOTA . brand em models passou ater uma lista de drinks
  getDrinksByBrand(brand: Brand): void {
    console.log('getDrinksByBrand ', brand.id);

    this.drinkService.getDrinksByBrand(brand.id).subscribe({
      next: (response: Drink[]) => {
        //Pego a mesma brand que recebi e coloco nela as bebidas que recebi do backend
        // Esta brand com drinks dentro será usada para carregar os dados na tabela interna
        brand.drinks = response;
      },
      error: (httpError: HttpErrorResponse) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro!',
          detail: httpError.statusText,
        });
      },
    });
  }


  openModalConfirmDialog(): void {
    this.confirmationService.confirm({
      message:
        'Tem a certeza que pretende apagar a marca ' +
        this.selectedBrand.name +
        '?',
      header: 'Confirmação',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.brandService.doSoftDeletBrand(this.selectedBrand.id).subscribe({
          error: (httpError: HttpErrorResponse) => {
            httpError.status == 409
              ? (this.errorText = httpError.error)
              : (this.errorText = 'Ocorreu um erro ao apagar a marca.');
            this.createToastTable('error', 'Erro!', this.errorText);
          },
          complete: () => {
            this.createToastTable(
              'success',
              'Dados excluidos!',
              'Marca apagada com sucesso!'
            ),
              this.getBrandsComponent();
            if (this.selectedBrand) {
              this.selectedBrand = null;
            }
          },
        });
      },
      reject: () => {
        this.createToastTable('warn', '', 'Operação cancelada');
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




  onSuccesResultAndUpdateNoPai(sucess: boolean) {
    if (sucess) {
      this.showModalNoComponentePai = false;
      this.getBrandsComponent();
    } else {
      this.createToastTable('error', 'Erro!', 'Erro ao criar marca!');
    }
  }


}
