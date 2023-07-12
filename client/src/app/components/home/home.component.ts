import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";

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
    console.log('hello')
    this.api.getQr().subscribe(response => {
      const reader = new FileReader();
      reader.onloadend = () => {
        this.qr = reader.result as string
      }
      reader.readAsDataURL(response);
    });
  }
}
