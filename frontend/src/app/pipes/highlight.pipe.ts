import { Pipe, PipeTransform } from '@angular/core';

//https://www.linkedin.com/pulse/angular-highlight-search-text-igor-gonchar/
@Pipe({
  name: 'highlight',
})
export class HighlightPipe implements PipeTransform {
  transform(wholeText: string, keyword: string): string {
    if (!keyword) {
      return wholeText;
    }
    const re = new RegExp(keyword, 'gi');
    return wholeText.replace(re, '<mark>$&</mark>');
  }
}
