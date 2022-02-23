import { FaceAuthenticatorCustomizationIos } from './customization';
import { SensorStabilitySettingsIos } from './sensor-stability-settings';
export declare class IosSettings {
    private customization;
    private beforePictureMillis;
    private sensorStability;
    constructor(customization: FaceAuthenticatorCustomizationIos, beforePictureMillis: number, sensorStability: SensorStabilitySettingsIos);
}
