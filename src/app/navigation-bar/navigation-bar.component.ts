import {Component, OnInit} from '@angular/core';
import {Tweet} from '../_models/tweet';
import {AccountService, TweetService} from '../_services';
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {
  query: string;
  queryResultAccount: Account[] = [];
  queryResultTweet: Tweet[] = [];
  searchForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private modalService: NgbModal,
    private accountService: AccountService,
    private tweetService: TweetService) {
    this.searchForm = this.fb.group({
      query: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  search() {
    const val = this.searchForm.value;
    this.query = val.query;
    this.accountService.search(val.query).subscribe(
      (data) => {
        console.log(data);
        this.queryResultAccount = data;

        this.tweetService.search(val.query).subscribe(
          (data2) => {
            this.queryResultTweet = data2;
          },
          (error) => {
            console.log(error);
          });
      },
      (error) => {
        console.log(error);
      });
  }

  open(content) {
    this.search();
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'});
  }
}
