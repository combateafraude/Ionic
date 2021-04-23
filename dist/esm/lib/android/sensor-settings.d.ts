import { SensorStabilitySettingsAndroid } from './sensor-stability-settings';
import { SensorOrientationSettingsAndroid } from './sensor-orientation-settings';
import { SensorLuminositySettingsAndroid } from './sensor-luminosity-settings';
export declare class SensorSettingsAndroid {
    private sensorLuminositySettings;
    private sensorOrientationSettings;
    private sensorStabilitySettings;
    constructor(sensorLuminositySettings: SensorLuminositySettingsAndroid, sensorOrientationSettings: SensorOrientationSettingsAndroid, sensorStabilitySettings: SensorStabilitySettingsAndroid);
}
