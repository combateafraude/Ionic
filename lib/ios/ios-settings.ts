import { PassiveFaceLivenessCustomizationIos } from './customization';
import { SensorStabilitySettingsIos } from './sensor-stability-settings';

export class IosSettings {
  private customization?: PassiveFaceLivenessCustomizationIos;
  private beforePictureMillis?: number;
  private sensorStability?: SensorStabilitySettingsIos;
  private enableManualCapture?: boolean;
  private timeEnableManualCapture?: number;
  private resolution?: string;
  private compressQuality?: number;

  constructor(
    options: {
      customization?: PassiveFaceLivenessCustomizationIos,
      beforePictureMillis?: number,
      sensorStability?: SensorStabilitySettingsIos,
      enableManualCapture?: boolean,
      timeEnableManualCapture?: number,
      resolution?: string,
      compressQuality?: number
    }) {
    this.customization = options?.customization;
    this.beforePictureMillis = options?.beforePictureMillis;
    this.sensorStability = options?.sensorStability;
    this.enableManualCapture = options?.enableManualCapture;
    this.timeEnableManualCapture = options?.timeEnableManualCapture;
    this.resolution = options?.resolution;
    this.compressQuality = options?.compressQuality
  }

}
