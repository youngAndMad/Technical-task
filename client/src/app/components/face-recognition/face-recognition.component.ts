import {Component, OnInit} from '@angular/core';
import * as faceapi from 'face-api.js'

@Component({
  selector: 'app-face-recognition',
  templateUrl: './face-recognition.component.html',
  styleUrls: ['./face-recognition.component.css']
})
export class FaceRecognitionComponent implements OnInit {
  private video: HTMLVideoElement;

  ngOnInit(): void {
    this.video = document.getElementById('video')! as HTMLVideoElement;

    Promise.all([
      faceapi.nets.ssdMobilenetv1.loadFromUri("/assets/weights"),
      faceapi.nets.faceRecognitionNet.loadFromUri("/assets/weights"),
      faceapi.nets.faceLandmark68Net.loadFromUri("/assets/weights"),
    ]).then(() => {
      navigator.mediaDevices
        .getUserMedia({
          video: true,
          audio: false,
        })
        .then((stream) => {
          this.video.srcObject = stream;
        })
        .catch((error) => {
          console.error(error);
        });
    });


    this.video.addEventListener("play", async () => {
      const labeledFaceDescriptors = await this.getLabeledFaceDescriptions();
      const faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors);

      const canvas = faceapi.createCanvasFromMedia(this.video)!;
      canvas.style.marginTop = "-70%"
      document.body.append(canvas);

      const displaySize = {width: this.video.width, height: this.video.height};
      faceapi.matchDimensions(canvas, displaySize);

      setInterval(async () => {
        const detections = await faceapi
          .detectAllFaces(this.video)
          .withFaceLandmarks()
          .withFaceDescriptors();

        const resizedDetections = faceapi.resizeResults(detections, displaySize);

        canvas.getContext("2d")?.clearRect(0, 0, canvas.width, canvas.height);

        const results = resizedDetections.map((d) => {
          return faceMatcher.findBestMatch(d.descriptor);
        });
        results.forEach((result, i) => {
          const box = resizedDetections[i].detection.box;
          const drawBox = new faceapi.draw.DrawBox(box, {
            label: "Daneker",
          })
          drawBox.draw(canvas);
        });
      }, 100);
    });
  }


  async getLabeledFaceDescriptions() {
    console.log('getLabeledFaceDescriptions')
    const descriptions = [];
    const img = await faceapi.fetchImage(`./assets/IMG_20230410_170438_116.jpg`);
    const detections = await faceapi
      .detectSingleFace(img)
      .withFaceLandmarks()
      .withFaceDescriptor();
    descriptions.push(detections!.descriptor);

    let to = new faceapi.LabeledFaceDescriptors('Daneker', descriptions);

    console.log(to)
    return to
  }
}
