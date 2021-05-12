import { CaptureAndroidSettings } from './capture-settings';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export class AndroidSettings {
   private customization: DocumentDetectorCustomizationAndroid;
   private sensorSettings: SensorSettingsAndroid;
   private captureSettings: CaptureAndroidSettings;
   private showButtonTime: number;

  constructor(
        customization: DocumentDetectorCustomizationAndroid,
        sensorSettings: SensorSettingsAndroid,
        ){
          this.customization = customization;
          this.sensorSettings = sensorSettings;
  }
}

