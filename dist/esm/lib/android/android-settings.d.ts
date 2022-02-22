import { SensorSettingsAndroid } from './sensor-settings';
import { FaceAuthenticatorCustomizationAndroid } from './customization';
export declare class AndroidSettings {
    private sensorSettings?;
    private showButtonTime?;
    private enableSwitchCameraButton?;
    private enableGoogleServices?;
    private useEmulator?;
    private useRoot?;
    private customization?;
    constructor(options: {
        sensorSettings?: SensorSettingsAndroid;
        showButtonTime?: number;
        enableSwitchCameraButton?: boolean;
        enableGoogleServices?: boolean;
        useEmulator?: boolean;
        useRoot?: boolean;
        customization?: FaceAuthenticatorCustomizationAndroid;
    });
}
