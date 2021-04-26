export class Capture{
    private imagePath: string;
    private imageUrl: string;
    private label: string;
    private quality: number;
  
    constructor(imagePath: string, imageUrl: string, label: string, quality: number){
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.label = label;
        this.quality = quality;
    }
  }