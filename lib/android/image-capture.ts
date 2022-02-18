export class ImageCapture {
    private use:boolean;
    private beforePictureMillis?: number;
    private afterPictureMillis?: number;
  
    constructor(
        options: {
          use:boolean,
          beforePictureMillis?: number
          afterPictureMillis?: number
        }){
            this.use = options.use;
            this.beforePictureMillis = options.beforePictureMillis;
            this.afterPictureMillis = options.afterPictureMillis;

      }

  }