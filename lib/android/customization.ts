export class DocumentDetectorCustomizationAndroid {
  private styleResIdName: String;
  private layoutResIdName: String;
  private greenMaskResIdName: String;
  private redMaskResIdName: String;
  private whiteMaskResIdName: String;



  constructor(
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
    }

}
/*
  Map asMap(){
    Map<String, dynamic> map = new Map();

    map["styleResIdName"] = styleResIdName;
    map["layoutResIdName"] = layoutResIdName;
    map["greenMaskResIdName"] = greenMaskResIdName;
    map["redMaskResIdName"] = redMaskResIdName;
    map["whiteMaskResIdName"] = whiteMaskResIdName;

    return map;
  }
}*/
