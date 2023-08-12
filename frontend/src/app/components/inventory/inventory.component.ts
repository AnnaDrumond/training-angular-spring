import { AppComponent } from './../../app.component';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Brand } from 'src/app/model/Brand';
import { BrandService } from 'src/app/service/brand.service';
import { ChartModule } from 'primeng/chart';
import { PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss'],
})
export class InventoryComponent implements OnInit {
  
  brands: Brand[];
  multiAxisOptions: any;
  multiAxisData: any;
  brandNames: string[];
  data: any;

  constructor(
    private brandService: BrandService,
    private appComponent: AppComponent,
    private primengConfig: PrimeNGConfig
  ) {}

  ngOnInit(): void {
    this.brandService.getBrands().subscribe({
      next: (response: any) => {
        this.brands = response;
        this.buildCharts();
      },
      error: (httpError: HttpErrorResponse) =>
        this.appComponent.createToastModalError(httpError.statusText),
    });
  }

  private buildCharts() {

    this.data = {
      labels: this.brands.map((a) => a.name),
      datasets: [
        {
          data: this.brands.map((a) => a.numberOfDrinks),
          backgroundColor: [
            '#EC407A',
            '#AB47BC',
            '#42A5F5',
            '#7E57C2',
            '#66BB6A',
            '#FFCA28',
            '#26A69A',
          ],
          hoverBackgroundColor: [
            '#EC407A',
            '#AB47BC',
            '#42A5F5',
            '#7E57C2',
            '#66BB6A',
            '#FFCA28',
            '#26A69A',
          ],
        },
      ],
    };

    //Para o gráfico de barras, manipular o array para tirar as marcas com quantidade de bebida igual a 0.
    //https://bobbyhadz.com/blog/typescript-remove-element-from-array
    this.brands = this.brands.filter((element) => {
      return element.numberOfDrinks > 0;
    })

    this.multiAxisData = {
      labels: this.brands.map((a) => a.name),
      datasets: [
        {
          label: 'Quantidade de bebidas por marca',
          backgroundColor: [
            '#EC407A',
            '#AB47BC',
            '#42A5F5',
            '#7E57C2',
            '#66BB6A',
            '#FFCA28',
            '#26A69A',
          ],
          yAxisID: 'y',
          data: this.brands.map((a) => a.numberOfDrinks),
        },
      ],
    };

    this.multiAxisOptions = {
      plugins: {
        legend: {
          labels: {
            color: '#495057',
          },
        },
        tooltips: {
          mode: 'index',
          intersect: true,
        },
      },
      scales: {
        x: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
        y: {
          type: 'linear',
          display: true,
          position: 'left',
          ticks: {
            min: 0,
            max: 100,
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
        y1: {
          type: 'linear',
          display: true,
          position: 'right',
          grid: {
            drawOnChartArea: false,
            color: '#ebedef',
          },
          ticks: {
            min: 0,
            max: 100,
            color: '#495057',
          },
        },
      },
    };


  }
}
