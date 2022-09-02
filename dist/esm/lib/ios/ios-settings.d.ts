import { PassiveFaceLivenessCustomizationIos } from './customization';
import { SensorStabilitySettingsIos } from './sensor-stability-settings';
export declare class IosSettings {
    private customization?;
    private beforePictureMillis?;
    private sensorStability?;
    private enableManualCapture?;
    private timeEnableManualCapture?;
    private resolution?;
    private compressQuality?;
    constructor(options: {
        customization?: PassiveFaceLivenessCustomizationIos;
        beforePictureMillis?: number;
        sensorStability?: SensorStabilitySettingsIos;
        enableManualCapture?: boolean;
        timeEnableManualCapture?: number;
        resolution?: string;
        compressQuality?: number;
    });
}
