import { SensorSettingsAndroid } from './sensor-settings';
import { PassiveFaceLivenessCustomizationAndroid } from './customization';
export declare class AndroidSettings {
    private customization?;
    private sensorSettings?;
    private showButtonTime?;
    private enableSwitchCameraButton?;
    private enableGoogleServices?;
    private useEmulator?;
    private useRoot?;
    private enableBrightnessIncrease?;
    private useDeveloperMode?;
    private useAdb?;
    private useDebug?;
    constructor(options: {
        customization?: PassiveFaceLivenessCustomizationAndroid;
        sensorSettings?: SensorSettingsAndroid;
        showButtonTime?: number;
        enableSwitchCameraButton?: boolean;
        enableGoogleServices?: boolean;
        useEmulator?: boolean;
        useRoot?: boolean;
        enableBrightnessIncrease?: boolean;
        useDeveloperMode?: boolean;
        useAdb?: boolean;
        useDebug?: boolean;
    });
}
