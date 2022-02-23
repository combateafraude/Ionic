import { FaceAuthenticatorCustomizationIos } from './customization';
import { SensorStabilitySettingsIos } from './sensor-stability-settings';

export class IosSettings {
  private customization: FaceAuthenticatorCustomizationIos;
  private beforePictureMillis: number;
  private sensorStability: SensorStabilitySettingsIos;

  constructor(
  customization: FaceAuthenticatorCustomizationIos, beforePictureMillis: number, sensorStability: SensorStabilitySettingsIos){
      this.customization = customization;
      this.beforePictureMillis = beforePictureMillis;
      this.sensorStability = sensorStability;
  }

}
