import {Component, Input, OnInit} from '@angular/core';
import {Tweet} from '../_models/tweet';
import {Account} from '../_models/account';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  @Input() accountResults: Account[];
  @Input() tweetResults: Tweet[];
  constructor() { }

  ngOnInit() {
  }

}
