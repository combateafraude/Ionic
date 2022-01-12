import { CaptureStage } from './capture-stage/capture-stage';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export class DocumentDetectorAndroidSettings {

  private customization: DocumentDetectorCustomizationAndroid;
   private sensorSettings: SensorSettingsAndroid;
   private captureStages: Array<CaptureStage>;
   private enableSwitchCameraButton: Boolean;
   private compressQuality: number;
   private resolution: String;
   private enableGoogleServices: boolean;
   private useRoot: boolean;
   private useEmulator: boolean;


  constructor(
        customization: DocumentDetectorCustomizationAndroid,
        sensorSettings: SensorSettingsAndroid,
        captureStages: Array<CaptureStage>,
        enableSwitchCameraButton: Boolean,
        compressQuality: number,
        resolution: String,
        enableGoogleServices: boolean,
        useRoot: boolean,
        useEmulator: boolean){
          this.customization = customization;
          this.sensorSettings = sensorSettings;
          this.captureStages = captureStages;
          this.enableSwitchCameraButton = enableSwitchCameraButton;
          this.compressQuality = compressQuality;
          this.resolution = resolution;
          this.enableGoogleServices = enableGoogleServices;
          this.useRoot = useRoot;
          this.useEmulator = useEmulator;
  }
}


/*Map asMap() {
    Map<String, dynamic> map = new Map();

    map["customization"] = customization?.asMap();
    map["sensorSettings"] = sensorSettings?.asMap();

    if (captureStages != null) {
      List<Map<String, dynamic>> stagesMap = [];
      for (var stage in captureStages!) {
        stagesMap.add(stage.asMap() as Map<String, dynamic>);
      }
      map["captureStages"] = stagesMap;
    }
    return map;
  }*/
