export class DocumentDetectorStepCustomizationAndroid {
  private stepLabelStringResName: string;
  private illustrationDrawableResName: string;
  private audioRawResName: string;

  constructor(
    stepLabelStringResName: string,
    illustrationDrawableResName: string,
    audioRawResName: string){
      this.stepLabelStringResName = stepLabelStringResName;
      this.illustrationDrawableResName = illustrationDrawableResName;
      this.audioRawResName = audioRawResName;
    }
}




  /*Map asMap(){
    Map<String, dynamic> map = new Map();

    map["stepLabelStringResName"] = stepLabelStringResName;
    map["illustrationDrawableResName"] = illustrationDrawableResName;
    map["audioRawResName"] = audioRawResName;

    return map;
  }*/
