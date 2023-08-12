import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { PageableSortableView } from '../model/PageableSort';
import { retry } from 'rxjs/operators';
import { lastValueFrom, Observable } from 'rxjs';
import { StorageDrink } from '../model/StorageDrink';

@Injectable({
  providedIn: 'root',
})
export class StorageService {

  readonly sort: string = 'sort';
  constructor(private httpClient: HttpClient) {}


  getStorages(): Observable<StorageDrink> {
    return this.httpClient.get<StorageDrink>(environment.API + '/storage/list');
  }

  getStoragesDrink( pageableSortableView: PageableSortableView<StorageDrink>, keyword: string, id: number): Observable<PageableSortableView<StorageDrink>> {
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
    return this.httpClient.get<PageableSortableView<StorageDrink>>(`${environment.API}storageDrink/byStorage/${id}`, {params: params,}).pipe(retry(2));
  }

  addDrinkInStorage(storageDrink: StorageDrink): Observable<StorageDrink> {
    return this.httpClient.post<StorageDrink>(`${environment.API}storageDrink`, storageDrink);
  }
}
