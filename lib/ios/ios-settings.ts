import { DocumentDetectorCustomizationIos } from './customization';
import { SensorSettingsIos } from './sensor_settings';

export class DocumentDetectorIosSettings {
  private detectionThreshold: number;
  private verifyQuality: boolean;
  private qualityThreshold: number;
  private customization: DocumentDetectorCustomizationIos;
  private sensorSettings: SensorSettingsIos;

  constructor(detectionThreshold: number, verifyQuality: boolean, qualityThreshold: number, customization: DocumentDetectorCustomizationIos, sensorSettings: SensorSettingsIos){
        this.detectionThreshold = detectionThreshold;
        this.verifyQuality = verifyQuality;
        this.qualityThreshold = qualityThreshold;
        this.customization = customization;
        this.sensorSettings = sensorSettings;
  }
}
