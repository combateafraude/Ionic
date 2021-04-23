export class DetectionSettings {
  private threshold: number;
  private consecutiveFrames: number;

  constructor(threshold: number, consecutiveFrames: number){
    this.threshold = threshold;
    this.consecutiveFrames = consecutiveFrames;
  }

  /*Map asMap(){
    Map<String, dynamic> map = new Map();

    map["threshold"] = threshold;
    map["consecutiveFrames"] = consecutiveFrames;

    return map;
  }*/
}
