import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  qr: string;

  constructor(private api: ApiService) {
  }

  ngOnInit(): void {
    this.api.getQr().subscribe(response => {
      if (response instanceof HttpErrorResponse) {
        alert('Error from backend')
      } else {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.qr = reader.result as string
        }
        reader.readAsDataURL(response);
      }
    });
  }
}
