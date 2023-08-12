import { StorageComponent } from './components/storage/storage.component';
import { InventoryComponent } from './components/inventory/inventory.component';
import { DrinkComponent } from './components/drink/drink.component';
import { BrandComponent } from './components/brand/brand.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/marcas' },
  { path: 'marcas', component: BrandComponent },
  { path: 'bebidas', component: DrinkComponent },
  { path: 'estoque', component: InventoryComponent },
  { path: 'armazens', component: StorageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [
  BrandComponent,
  DrinkComponent,
  InventoryComponent,
  StorageComponent,
];
