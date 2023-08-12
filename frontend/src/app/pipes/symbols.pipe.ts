import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'symbols',
})
export class SymbolsPipe implements PipeTransform {
  nvalue: string;

  transform(value: string, symbol = ' ml'): string {
    this.nvalue = value + symbol;
    return this.nvalue;
  }
}
