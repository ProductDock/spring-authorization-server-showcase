import {Component, OnInit} from '@angular/core';
import {MaterialModule} from "../material/material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {HttpClient} from "@angular/common/http";
import {Book} from "./book.model";
import {MatTableDataSource} from "@angular/material/table";


@Component({
  selector: 'app-books',
  standalone: true,
  imports: [MaterialModule, FlexLayoutModule],
  templateUrl: './books.component.html',
  styleUrl: './books.component.css'
})
export class BooksComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'price'];
  dataSource = new MatTableDataSource<Book>();

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get<Book[]>("/api/v1/books")
      .subscribe({
        next: (books) => this.dataSource.data = [...books],
        error: err => console.log(err)
      })
  }


  onBuy(id: number) {
    this.http.post(`/api/v1/books/${id}`, {})
      .subscribe({
        next: () => console.log("Successful"),
        error: (err) => console.log(err)
      })
  }
}
