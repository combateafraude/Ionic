import { DocumentDetectorCustomizationIos } from './customization';
import { SensorSettingsIos } from './sensor_settings';

export class DocumentDetectorIosSettings {
  private detectionThreshold: number;
  private verifyQuality: boolean;
  private qualityThreshold: number;
  private customization: DocumentDetectorCustomizationIos;
  private sensorSettings: SensorSettingsIos;
  private manualCaptureEnable: boolean;
  private manualCaptureTime: number; 

  constructor(detectionThreshold: number, verifyQuality: boolean, qualityThreshold: number, customization: DocumentDetectorCustomizationIos, sensorSettings: SensorSettingsIos, manualCaptureEnable: boolean, manualCaptureTime: number){
        this.detectionThreshold = detectionThreshold;
        this.verifyQuality = verifyQuality;
        this.qualityThreshold = qualityThreshold;
        this.customization = customization;
        this.sensorSettings = sensorSettings;
        this.manualCaptureEnable = manualCaptureEnable;
        this.manualCaptureTime = manualCaptureTime;
  }
}
