import { SensorStabilitySettingsAndroid } from './sensor-stability-settings';
import { SensorOrientationSettingsAndroid } from './sensor-orientation-settings';
import { SensorLuminositySettingsAndroid } from './sensor-luminosity-settings';

export class SensorSettingsAndroid {
  private sensorLuminositySettings: SensorLuminositySettingsAndroid;
  private sensorOrientationSettings: SensorOrientationSettingsAndroid;
  private sensorStabilitySettings: SensorStabilitySettingsAndroid;

  constructor(
    sensorLuminositySettings: SensorLuminositySettingsAndroid,
    sensorOrientationSettings: SensorOrientationSettingsAndroid,
    sensorStabilitySettings: SensorStabilitySettingsAndroid){
      this.sensorLuminositySettings = sensorLuminositySettings;
      this.sensorOrientationSettings = sensorOrientationSettings;
      this.sensorStabilitySettings = sensorStabilitySettings;
    }
}