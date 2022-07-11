export class UploadSettings{
  private activityLayout:string;
  private popUpLayout:string;
  private compress:boolean;
  private maxFileSize:number;
  private intent:string;
  private fileFormats:FileFormats;

  constructor(activityLayout:string, popUpLayout:string, compress:boolean, maxFileSize:number,intent:string, fileFormats:FileFormats){
    this.activityLayout = activityLayout;
    this.popUpLayout = popUpLayout;
    this.compress = compress;
    this.maxFileSize = maxFileSize;
    this.intent = intent;
    this.fileFormats = fileFormats;
  }

}
