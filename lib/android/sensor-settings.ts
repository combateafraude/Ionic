import { SensorStabilitySettingsAndroid } from './sensor-stability-settings';
import { SensorOrientationSettingsAndroid } from './sensor-orientation-settings';

export class SensorSettingsAndroid {
  private sensorStabilitySettings: SensorStabilitySettingsAndroid;
  private sensorOrientationSettings: SensorOrientationSettingsAndroid;

  constructor(
    sensorStabilitySettings: SensorStabilitySettingsAndroid,
    sensorOrientationSettings: SensorOrientationSettingsAndroid) {
    this.sensorStabilitySettings = sensorStabilitySettings;
    this.sensorOrientationSettings = sensorOrientationSettings;
  }
}