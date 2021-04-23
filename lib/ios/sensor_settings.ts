import { SensorStabilitySettingsIos } from './sensor_stability_settings';
import { SensorOrientationSettingsIos } from './sensor_orientation_settings';
import { SensorLuminositySettingsIos } from './sensor_luminosity_settings';


export class SensorSettingsIos {
  private sensorLuminosity: SensorLuminositySettingsIos;
  private sensorOrientation: SensorOrientationSettingsIos;
  private sensorStability: SensorStabilitySettingsIos;

  constructor(sensorLuminosity: SensorLuminositySettingsIos, sensorOrientation: SensorOrientationSettingsIos, sensorStability: SensorStabilitySettingsIos){
      this.sensorLuminosity = sensorLuminosity;
      this.sensorOrientation = sensorOrientation;
      this.sensorStability = sensorStability;
  }
}
