import { SensorSettingsAndroid } from './sensor-settings';
import { FaceAuthenticatorCustomizationAndroid } from './customization';
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
        customization?: FaceAuthenticatorCustomizationAndroid;
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
