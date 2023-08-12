import { Component, OnInit } from '@angular/core';
import { MenuItem, PrimeIcons } from 'primeng/api';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit {
  menuItems: MenuItem[];

  constructor() {}

  ngOnInit(): void {
    this.menuItems = [
      { label: 'Marcas', icon: PrimeIcons.TH_LARGE, routerLink: ['/marcas'] },
      { label: 'Bebidas', icon: PrimeIcons.BOOKMARK_FILL, routerLink: ['/bebidas'] },
      { label: 'Estoque', icon: PrimeIcons.CHART_BAR, routerLink: ['/estoque'] },
      { label: 'Armazens', icon: PrimeIcons.BUILDING, routerLink: ['/armazens'] },
    ];
  }
}
