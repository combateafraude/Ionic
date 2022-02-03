export class SensorLuminositySettingsAndroid {
  private messageResourceIdName: string;
  private luminosityThreshold: number;

  constructor(
  messageResourceIdName: string, luminosityThreshold: number){
    this.messageResourceIdName = messageResourceIdName;
    this.luminosityThreshold = luminosityThreshold;
  }
}
