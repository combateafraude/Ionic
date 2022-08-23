export class Capture{
    private imagePath: string;
    private imageUrl: string;
    private label: string;
    private quality: number;
    private lensFacing: number;
  
    constructor(imagePath: string, imageUrl: string, label: string, quality: number, lensFacing: number){
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.label = label;
        this.quality = quality;
        this.lensFacing = lensFacing;
    }
  }