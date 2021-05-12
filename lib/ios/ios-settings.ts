import { PassiveFaceLivenessCustomizationIos } from './customization';
import { SensorStabilitySettingsIos } from './sensor-stability-settings';

export class IosSettings {
  private customization: PassiveFaceLivenessCustomizationIos;
  private beforePictureMillis: number;
  private sensorStability: SensorStabilitySettingsIos;

  constructor(
  customization: PassiveFaceLivenessCustomizationIos, beforePictureMillis: number, sensorStability: SensorStabilitySettingsIos){
      this.customization = customization;
      this.beforePictureMillis = beforePictureMillis;
      this.sensorStability = sensorStability;
  }

}
