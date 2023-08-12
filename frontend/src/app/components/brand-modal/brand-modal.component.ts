import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Brand } from 'src/app/model/Brand';
import { BrandService } from 'src/app/service/brand.service';

@Component({
  selector: 'app-brand-modal',
  templateUrl: './brand-modal.component.html',
  styleUrls: ['./brand-modal.component.scss'],
})
export class BrandModalComponent implements OnInit {
  name: any;
  source: any;
  @Input() onShowModalNoFilhoRecebeValorDoPai: boolean;
  @Output() hideModalNoComponenteFilhoEnviaValorParaPai = new EventEmitter<boolean>();
  @Output() succesResultAndUpdateNoFilho = new EventEmitter<boolean>();
  submitted: boolean = false;

  constructor(
    private brandService: BrandService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  onAddBrand(dataBrandForm: NgForm) {
    this.submitted = true;
    if (dataBrandForm.invalid) {
      this.createToastModal('Não podem existir campos vazios!');
    } else {
      this.brandService.addBrand(dataBrandForm.value).subscribe({
        next: (response: Brand) => {
          dataBrandForm.reset(),
            this.createToastTable(
              'success',
              'Dados guardados!',
              'Marca criada com sucesso!'
            );
          if (response) {
            this.succesResultAndUpdateNoFilho.emit(true);
          }
        },
        error: () => this.succesResultAndUpdateNoFilho.emit(false),
      });
    }
  }

  cancelActionModal(dataBrandForm: NgForm) {
    if (dataBrandForm) {
      dataBrandForm.reset();
    }
    let showModal: boolean = false;
    this.hideModalNoComponenteFilhoEnviaValorParaPai.emit(showModal);
    this.createToastTable('warn', '', 'Operação cancelada');
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
