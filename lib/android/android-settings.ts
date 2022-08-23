import { CaptureStage } from './capture-stage/capture-stage';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export class DocumentDetectorAndroidSettings {

  private customization?: DocumentDetectorCustomizationAndroid;
   private sensorSettings?: SensorSettingsAndroid;
   private captureStages?: Array<CaptureStage>;
   private enableSwitchCameraButton?: Boolean;
   private compressQuality?: number;
   private resolution?: String;
   private enableGoogleServices?: boolean;
   private useRoot?: boolean;
   private useEmulator?: boolean;
   private useDeveloperMode?: boolean;
   private useAdb?: boolean;
   private useDebug?: boolean;

  constructor(
    options: {
        customization?: DocumentDetectorCustomizationAndroid,
        sensorSettings?: SensorSettingsAndroid,
        captureStages?: Array<CaptureStage>,
        enableSwitchCameraButton?: Boolean,
        compressQuality?: number,
        resolution?: String,
        enableGoogleServices?: boolean,
        useRoot?: boolean,
        useEmulator?: boolean,
        useDeveloperMode?: boolean,
        useAdb?: boolean,
        useDebug?: boolean,
        }){

          this.customization = options.customization;
          this.sensorSettings = options.sensorSettings;
          this.captureStages = options.captureStages;
          this.enableSwitchCameraButton = options.enableSwitchCameraButton;
          this.compressQuality = options.compressQuality;
          this.resolution = options.resolution;
          this.enableGoogleServices = options.enableGoogleServices;
          this.useRoot = options.useRoot;
          this.useEmulator = options.useEmulator;
          this.useDeveloperMode = options.useDeveloperMode;
          this.useAdb = options.useAdb;
          this.useDebug = options.useDebug;
  }
}
