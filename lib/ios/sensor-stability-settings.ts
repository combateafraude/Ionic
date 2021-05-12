export class SensorStabilitySettingsIos {
    private message: string;
    private stabilityThreshold: number;
  
    constructor(message: string, stabilityThreshold: number){
        this.message = message;
        this.stabilityThreshold = stabilityThreshold;
    }
  }
  