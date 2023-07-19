import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {FaceRecognitionComponent} from "./components/face-recognition/face-recognition.component";

const routes: Routes = [
  {path: '', component: FaceRecognitionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
