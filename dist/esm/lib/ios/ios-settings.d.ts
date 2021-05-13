import { PassiveFaceLivenessCustomizationIos } from './customization';
import { SensorStabilitySettingsIos } from './sensor-stability-settings';
export declare class IosSettings {
    private customization;
    private beforePictureMillis;
    private sensorStability;
    constructor(customization: PassiveFaceLivenessCustomizationIos, beforePictureMillis: number, sensorStability: SensorStabilitySettingsIos);
}
