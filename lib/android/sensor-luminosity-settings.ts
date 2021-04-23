export class SensorLuminositySettingsAndroid {
  private messageResourceIdName: string;
  private luminosityThreshold: number;

  constructor(
  messageResourceIdName: string, luminosityThreshold: number){
    this.messageResourceIdName = messageResourceIdName;
    this.luminosityThreshold = luminosityThreshold;
  }

 /* Map asMap(){
    Map<String, dynamic> map = new Map();

    map["messageResourceIdName"] = messageResourceIdName;
    map["luminosityThreshold"] = luminosityThreshold;

    return map;
  }*/
}
