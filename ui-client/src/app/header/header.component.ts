import {Component, OnInit} from '@angular/core';
import {MaterialModule} from "../material/material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {HttpClient} from "@angular/common/http";

export interface User {
  username: string;
}

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MaterialModule, FlexLayoutModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  username = '';

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get<User>('/api/v1/users/me').subscribe({
      next: (response: User) => (this.username = response.username),
      error: (err) => console.log(err),
    });
  }


}
