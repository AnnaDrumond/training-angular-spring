import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'Airc-Training';

  constructor(private messageService: MessageService) {}

  createToastModalError(detailToast: string): void {
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
