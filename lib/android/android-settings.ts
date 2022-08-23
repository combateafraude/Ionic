
import { SensorSettingsAndroid } from './sensor-settings';
import { PassiveFaceLivenessCustomizationAndroid } from './customization';
export class AndroidSettings {
  private customization?: PassiveFaceLivenessCustomizationAndroid;
  private sensorSettings?: SensorSettingsAndroid;
  private showButtonTime?: number;
  private enableSwitchCameraButton?: boolean;
  private enableGoogleServices?: boolean;
  private useEmulator?: boolean;
  private useRoot?: boolean;
  private enableBrightnessIncrease?: boolean;
  private useDeveloperMode?: boolean;
  private useAdb?: boolean;
  private useDebug?: boolean;

  constructor(
    options: {
      customization?: PassiveFaceLivenessCustomizationAndroid,
      sensorSettings?: SensorSettingsAndroid,
      showButtonTime?: number,
      enableSwitchCameraButton?: boolean,
      enableGoogleServices?: boolean,
      useEmulator?: boolean,
      useRoot?: boolean,
      enableBrightnessIncrease?: boolean,
      useDeveloperMode?: boolean,
      useAdb?: boolean,
      useDebug?: boolean
    }) {
    this.customization = options?.customization;
    this.sensorSettings = options?.sensorSettings;
    this.showButtonTime = options?.showButtonTime;
    this.enableSwitchCameraButton = options?.enableSwitchCameraButton;
    this.enableGoogleServices = options?.enableGoogleServices;
    this.useEmulator = options?.useEmulator;
    this.useRoot = options?.useRoot;
    this.enableBrightnessIncrease = options?.enableBrightnessIncrease;
    this.useDeveloperMode = options?.useDeveloperMode;
    this.useAdb = options?.useAdb;
    this.useDebug = options?.useDebug;

  }
}

