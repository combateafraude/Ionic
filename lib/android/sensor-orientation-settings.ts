export class SensorOrientationSettingsAndroid {
  private messageResourceIdName: string;
  private orientationThreshold: number;

  constructor(
  messageResourceIdName: string, orientationThreshold: number){
    this.messageResourceIdName = messageResourceIdName;
    this.orientationThreshold = orientationThreshold;
  }

  /*Map asMap(){
    Map<String, dynamic> map = new Map();

    map["messageResourceIdName"] = messageResourceIdName;
    map["orientationThreshold"] = orientationThreshold;

    return map;
  }*/
}
