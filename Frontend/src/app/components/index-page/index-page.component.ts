import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-index-page',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent implements OnInit {
  searchForm: FormGroup = new FormGroup({});
  errorMessage: string[] = [];

  constructor(private fb: FormBuilder, private route: Router) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.searchForm = this.fb.group({
      awb: [null, [Validators.required]]
    });
  }

  search() {
    this.errorMessage = [];
    if(this.searchForm.valid) {
      this.route.navigateByUrl("/vezi-coletul/" + this.searchForm.get("awb")?.value);
    } else {
      this.errorMessage.push("Toate campurile trebuie completate!");
    }
  }
}
