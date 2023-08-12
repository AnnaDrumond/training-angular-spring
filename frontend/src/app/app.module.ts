import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { MenubarModule } from 'primeng/menubar';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { ProgressBarModule } from 'primeng/progressbar';
import { TableModule } from 'primeng/table';
import { TabMenuModule } from 'primeng/tabmenu';
import { ToastModule } from 'primeng/toast';
import { KnobModule } from "primeng/knob";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrandModalComponent } from './components/brand-modal/brand-modal.component';
import { BrandComponent } from './components/brand/brand.component';
import { DrinkModalComponent } from './components/drink-modal/drink-modal.component';
import { DrinkComponent } from './components/drink/drink.component';
import { InventoryComponent } from './components/inventory/inventory.component';
import { MenuComponent } from './components/menu/menu.component';
import { StorageModalComponent } from './components/storage-modal/storage-modal.component';
import { StorageComponent } from './components/storage/storage.component';
import { HighlightPipe } from './pipes/highlight.pipe';
import { SymbolsPipe } from './pipes/symbols.pipe';

@NgModule({
  declarations: [
    AppComponent,
    BrandComponent,
    DrinkComponent,
    MenuComponent,
    DrinkModalComponent,
    BrandModalComponent,
    InventoryComponent,
    HighlightPipe,
    SymbolsPipe,
    StorageComponent,
    StorageModalComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MenubarModule,
    MessageModule,
    TabMenuModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MessagesModule,
    FormsModule,
    TableModule,
    CardModule,
    ProgressBarModule,
    ButtonModule,
    DropdownModule,
    DialogModule,
    InputNumberModule,
    ToastModule,
    ReactiveFormsModule,
    ConfirmDialogModule,
    ChartModule,
    DividerModule,
    KnobModule
  ],
  providers: [MessageService, ConfirmationService],
  bootstrap: [AppComponent],
})
export class AppModule {}
