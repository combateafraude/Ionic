import { CaptureAndroidSettings } from './capture-settings';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export class AndroidSettings {
   private customization?: DocumentDetectorCustomizationAndroid;
   private sensorSettings?: SensorSettingsAndroid;
   private captureSettings?: CaptureAndroidSettings;
   private showButtonTime?: number;
   private enableSwitchCameraButton?: boolean;
   private enableGoogleServices?: boolean;
   private useEmulator?: boolean;
   private useRoot?: boolean;

  constructor(
    options: {
      customization?: DocumentDetectorCustomizationAndroid,
      sensorSettings?: SensorSettingsAndroid,
      captureSettings?: CaptureAndroidSettings,
      showButtonTime?: number,
      enableSwitchCameraButton?: boolean,
      enableGoogleServices?: boolean,
      useEmulator?: boolean,
      useRoot?: boolean
    }){
          this.customization = options?.customization;
          this.sensorSettings = options?.sensorSettings;
          this.captureSettings = options?.captureSettings;
          this.showButtonTime = options?.showButtonTime;
          this.enableSwitchCameraButton = options?.enableSwitchCameraButton;
          this.enableGoogleServices = options?.enableGoogleServices;
          this.useEmulator = options?.useEmulator;
          this.useRoot = options?.useRoot;
  }
}

