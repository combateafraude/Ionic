import { CaptureAndroidSettings } from './capture-settings';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export declare class AndroidSettings {
    private customization?;
    private sensorSettings?;
    private captureSettings?;
    private showButtonTime?;
    private enableSwitchCameraButton?;
    private enableGoogleServices?;
    private useEmulator?;
    private useRoot?;
    constructor(options: {
        customization?: DocumentDetectorCustomizationAndroid;
        sensorSettings?: SensorSettingsAndroid;
        captureSettings?: CaptureAndroidSettings;
        showButtonTime?: number;
        enableSwitchCameraButton?: boolean;
        enableGoogleServices?: boolean;
        useEmulator?: boolean;
        useRoot?: boolean;
    });
}
