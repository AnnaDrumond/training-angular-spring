import { TypeDrink } from './../model/Drink';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry } from 'rxjs';
import { PageableSortableView } from 'src/app/model/PageableSort';

import { environment } from '../../environments/environment';
import { Drink } from '../model/Drink';

@Injectable({
  providedIn: 'root',
})
export class DrinkService {
  readonly sort: string = 'sort';
  constructor(private httpClient: HttpClient) {}

  getDrinks( keyword: string,  pageableSortableView: PageableSortableView<Drink>): Observable<PageableSortableView<Drink>> {
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
    return this.httpClient
      .get<PageableSortableView<Drink>>(`${environment.API}drink/list/active`, {
        params: params,
      })
      .pipe(retry(2));
  }

  getTypesDrink(): Observable<TypeDrink[]> {
    return this.httpClient
      .get<TypeDrink[]>(`${environment.API}/drink/enums/list`)
      .pipe(retry(2));
  }

  createDrink(drink: Drink): Observable<Drink> {
    return this.httpClient.post<Drink>(`${environment.API}/drink`, drink);
  }

  editDrink(drink: Drink, id: number): Observable<Drink> {
    return this.httpClient.put<Drink>(`${environment.API}/drink/${id}`, drink);
  }

  doSoftDeletDrinks(id: number) {
    return this.httpClient.delete<Drink>(`${environment.API}/drink/${id}`);
  }

  getDrinksByBrand(id: number): Observable<Drink[]> {
    return this.httpClient
      .get<Drink[]>(`${environment.API}drink/byBrand/${id}`)
      .pipe(retry(2));
  }

  getDrinksSimple(): Observable<Drink[]> {
    return this.httpClient
      .get<Drink[]>(`${environment.API}drink/all/simple`)
      .pipe(retry(2));
  }
}
