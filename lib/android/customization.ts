export class DocumentDetectorCustomizationAndroid {
  private styleResIdName: String;
  private layoutResIdName: String;
  private greenMaskResIdName: String;
  private redMaskResIdName: String;
  private whiteMaskResIdName: String;
  private maskType: String;

  constructor(
    maskType: String,
  styleResIdName: String,
    layoutResIdName: String,
    greenMaskResIdName: String,
    redMaskResIdName: String,
    whiteMaskResIdName: String){
      this.styleResIdName = styleResIdName;
      this.layoutResIdName = layoutResIdName;
      this.greenMaskResIdName = greenMaskResIdName;
      this.redMaskResIdName = redMaskResIdName;
      this.whiteMaskResIdName = whiteMaskResIdName;
      this.maskType = maskType;
    }
}