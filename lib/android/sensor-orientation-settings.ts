export class SensorOrientationSettingsAndroid {
  private messageResourceIdName: string;
  private stabilityThreshold: number;

  constructor(messageResourceIdname: string, stabilityThreshold: number) {
    this.messageResourceIdName = messageResourceIdname;
    this.stabilityThreshold = stabilityThreshold;
  }
}