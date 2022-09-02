import { DocumentDetectorCustomizationIos } from './customization';
import { SensorSettingsIos } from './sensor_settings';

export class DocumentDetectorIosSettings {
  private detectionThreshold: number;
  private verifyQuality: boolean;
  private qualityThreshold: number;
  private customization: DocumentDetectorCustomizationIos;
  private sensorSettings: SensorSettingsIos;
  private enableManualCapture: boolean;
  private timeEnableManualCapture: number;
  private resolution: string;
  private compressQuality: number;

  constructor(
    detectionThreshold: number,
    verifyQuality: boolean,
    qualityThreshold: number,
    customization: DocumentDetectorCustomizationIos,
    sensorSettings: SensorSettingsIos,
    enableManualCapture: boolean,
    timeEnableManualCapture: number,
    resolution: string,
    compressQuality: number
  ) {
    this.detectionThreshold = detectionThreshold;
    this.verifyQuality = verifyQuality;
    this.qualityThreshold = qualityThreshold;
    this.customization = customization;
    this.sensorSettings = sensorSettings;
    this.enableManualCapture = enableManualCapture;
    this.timeEnableManualCapture = timeEnableManualCapture;
    this.resolution = resolution;
    this.compressQuality = compressQuality;
  }
}
