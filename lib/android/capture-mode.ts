import { VideoCapture } from './video-capture';
import { ImageCapture } from './image-capture';
export class CaptureMode {
    private videoCapture?:VideoCapture;
    private imageCapture?: ImageCapture;  
    constructor(
        options: {
          videoCapture?:VideoCapture,
          imageCapture?: ImageCapture
        }){
            this.videoCapture = options.videoCapture;
            this.imageCapture = options.imageCapture;
      }
  }