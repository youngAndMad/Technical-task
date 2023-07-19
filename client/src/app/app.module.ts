import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import {HttpClientModule} from "@angular/common/http";
import {NgOptimizedImage} from "@angular/common";
import { FaceRecognitionComponent } from './components/face-recognition/face-recognition.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, FaceRecognitionComponent],
  imports: [BrowserModule, HttpClientModule, AppRoutingModule, NgOptimizedImage],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
