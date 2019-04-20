import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Tweet} from '../_models/tweet';

@Injectable()
export class TweetService {

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Tweet[]>('http://localhost:8080/Kwetter/api/tweet');
  }
}
