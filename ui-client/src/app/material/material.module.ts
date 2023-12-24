import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatDividerModule} from "@angular/material/divider";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatTableModule} from "@angular/material/table";

const material = [
  MatDividerModule,
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  MatTableModule]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    material
  ],
  exports: [material]
})
export class MaterialModule {
}
