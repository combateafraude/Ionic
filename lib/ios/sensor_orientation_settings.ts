export class SensorOrientationSettingsIos {
    private message: string;
    private orientationThreshold: number;
  
    constructor(message: string, orientationThreshold: number){
        this.message = message;
        this.orientationThreshold = orientationThreshold;
    }
  }
  