import { CaptureAndroidSettings } from './capture-settings';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export class AndroidSettings {
   private customization: DocumentDetectorCustomizationAndroid;
   private sensorSettings: SensorSettingsAndroid;
   private captureSettings: CaptureAndroidSettings;
   private showButtonTime: number;
   private enableSwitchCameraButton: boolean;
   private enableGoogleServices: boolean;
   private useEmulator: boolean;
   private useRoot: boolean;

  constructor(
        customization: DocumentDetectorCustomizationAndroid,
        sensorSettings: SensorSettingsAndroid,
        captureSettings: CaptureAndroidSettings,
        showButtonTime: number,
        enableSwitchCameraButton: boolean,
        enableGoogleServices: boolean,
        useEmulator: boolean,
        useRoot: boolean
        ){
          this.customization = customization;
          this.sensorSettings = sensorSettings;
          this.captureSettings = captureSettings;
          this.showButtonTime = showButtonTime;
          this.enableSwitchCameraButton = enableSwitchCameraButton;
          this.enableGoogleServices = enableGoogleServices;
          this.useEmulator = useEmulator;
          this.useRoot = useRoot;
  }
}

