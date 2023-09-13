import { Component, Input } from '@angular/core';

@Input() message: string;

@Component({
  selector: 'post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})

export class PostComponent {
  let message: string = '';
  
  constructor() {
    this.message = '';
  } 
}
