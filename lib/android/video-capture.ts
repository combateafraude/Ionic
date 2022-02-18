export class VideoCapture {
    private use:boolean;
    private time?: number;
  
    constructor(
        options: {
          use:boolean,
          time?: number
        }){
            this.use = options.use;
            this.time = options.time;
      }

  }