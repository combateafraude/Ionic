import { SensorStabilitySettingsIos } from './sensor_stability_settings';
import { SensorOrientationSettingsIos } from './sensor_orientation_settings';
import { SensorLuminositySettingsIos } from './sensor_luminosity_settings';
export declare class SensorSettingsIos {
    private sensorLuminosity;
    private sensorOrientation;
    private sensorStability;
    constructor(sensorLuminosity: SensorLuminositySettingsIos, sensorOrientation: SensorOrientationSettingsIos, sensorStability: SensorStabilitySettingsIos);
}
