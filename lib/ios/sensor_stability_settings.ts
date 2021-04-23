export class SensorStabilitySettingsIos {
    private message: string;
    private stabilityhreshold: number;

    constructor(message: string, stabilityhreshold: number){
        this.message = message;
        this.stabilityhreshold = stabilityhreshold;
    }
  }
  