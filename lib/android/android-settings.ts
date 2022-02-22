
import { SensorSettingsAndroid } from './sensor-settings';
import {FaceAuthenticatorCustomizationAndroid} from './customization'
export class AndroidSettings {
   private sensorSettings?: SensorSettingsAndroid;
   private showButtonTime?: number;
   private enableSwitchCameraButton?: boolean;
   private enableGoogleServices?: boolean;
   private useEmulator?: boolean;
   private useRoot?: boolean;
   private customization?: FaceAuthenticatorCustomizationAndroid;

  constructor(
    options: {
      sensorSettings?: SensorSettingsAndroid,
      showButtonTime?: number,
      enableSwitchCameraButton?: boolean,
      enableGoogleServices?: boolean,
      useEmulator?: boolean,
      useRoot?: boolean,
      customization?: FaceAuthenticatorCustomizationAndroid
    }){
          this.sensorSettings = options?.sensorSettings;
          this.showButtonTime = options?.showButtonTime;
          this.enableSwitchCameraButton = options?.enableSwitchCameraButton;
          this.enableGoogleServices = options?.enableGoogleServices;
          this.useEmulator = options?.useEmulator;
          this.useRoot = options?.useRoot;
          this.customization = options?.customization;
  }
}

