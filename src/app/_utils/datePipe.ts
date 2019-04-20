import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';
@Pipe({
  name: 'datex'
})

export class DatexPipe implements PipeTransform {
  transform(value: any, format: string = ''): string {
    // Try and parse the passed value.
    const formatted = moment(value, 'YYYY-MM-DDTHH:mm:ss.SSSZ');
    const momentDate = moment(formatted);

    // If moment didn't understand the value, return it unformatted.
    if (!momentDate.isValid()) {
      return value;
    }

    // Otherwise, return the date formatted as requested.
    return momentDate.format(format);
  }
}
