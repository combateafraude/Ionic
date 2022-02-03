export class SensorOrientationSettingsAndroid {
  private messageResourceIdName: string;
  private orientationThreshold: number;

  constructor(
  messageResourceIdName: string, orientationThreshold: number){
    this.messageResourceIdName = messageResourceIdName;
    this.orientationThreshold = orientationThreshold;
  }
}
