import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { PageableSortableView } from '../model/PageableSort';
import { Brand } from './../model/Brand';

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  readonly sort: string = 'sort';
  constructor(private httpClient: HttpClient) {}

  getBrandsWithPaginatorAndSort( pageableSortableView: PageableSortableView<Brand>, keyword: string): Observable<PageableSortableView<Brand>> {

    let params = new HttpParams();

    if (pageableSortableView) {
      params = params.append('page', pageableSortableView.pageable.pageNumber);
      if (pageableSortableView.pageable.sort.property) {
        params = params.append(
          this.sort,
          pageableSortableView.pageable.sort.property +
            ',' +
            pageableSortableView.pageable.sort.direction
        );
      }
      params = params.append('search', keyword);
    }

    console.log("params " , params);

    return this.httpClient
      .get<PageableSortableView<Brand>>(environment.API + 'brand/paginator', {
        params: params,
      })
      .pipe(retry(2));
  }

  doSoftDeletBrand(id: number) {
    return this.httpClient.delete<Brand>(`${environment.API}/brand/${id}`);
  }

  addBrand(brand: Brand): Observable<Brand> {
    return this.httpClient.post<Brand>(`${environment.API}/brand`, brand);
  }

  getBrands(): Observable<Brand> {
    return this.httpClient
      .get<Brand>(environment.API + 'brand/list')
      .pipe(retry(2));
  }

  //Usando async await - Observables não aceitam async, por isso usar Promisses
  /*
No service:
  async getBrandsAwait(): Promise<Brand[]> {
    return await lastValueFrom(
      this.httpClient.get<Brand[]>(environment.API + 'brand/list')
    );
  }
No componente:
 /*async getBrands() {
    await this.brandService.getBrandsAwait().then((brands) => {
      console.log('dentro ', brands);
      this.brands = brands;
      //this.teste(brands);
    });
  }*/
}
