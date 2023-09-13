import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'main-feed',
  templateUrl: './main-feed.component.html',
  styleUrls: ['./main-feed.component.css']
})
export class MainFeedComponent {
  messageFromParent = 'Helou';
  itemArray = new Array(5);
}
