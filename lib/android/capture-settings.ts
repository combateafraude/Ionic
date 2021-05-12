export class CaptureAndroidSettings {
    private beforePictureMillis: number;
    private afterPictureMillis: number;
  
    constructor(beforePictureMillis: number, afterPictureMillis: number){
        this.beforePictureMillis = beforePictureMillis;
        this.afterPictureMillis = afterPictureMillis;
    }

  }