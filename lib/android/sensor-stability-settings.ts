export class SensorStabilitySettingsAndroid {
  private messageResourceIdName: string;
  private stabilityStabledMillis: number;
  private stabilityThreshold: number;

  constructor(
      messageResourceIdName: string,
      stabilityStabledMillis: number,
      stabilityThreshold: number){
        this.messageResourceIdName = messageResourceIdName;
        this.stabilityStabledMillis = stabilityStabledMillis;
        this.stabilityThreshold = stabilityThreshold;
      }
}
