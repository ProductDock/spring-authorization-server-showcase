import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';
import {HttpClient} from "@angular/common/http";
import {MaterialModule} from "./material/material.module";
import {HeaderComponent} from "./header/header.component";
import {BooksComponent} from "./books/books.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MaterialModule, HeaderComponent, BooksComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'ui-client';

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {

  }


}
