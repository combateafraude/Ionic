import { CaptureStage } from './capture-stage/capture-stage';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export declare class DocumentDetectorAndroidSettings {
    private customization?;
    private sensorSettings?;
    private captureStages?;
    private enableSwitchCameraButton?;
    private compressQuality?;
    private resolution?;
    private enableGoogleServices?;
    private useRoot?;
    private useEmulator?;
    private useDeveloperMode?;
    private useAdb?;
    private useDebug?;
    constructor(options: {
        customization?: DocumentDetectorCustomizationAndroid;
        sensorSettings?: SensorSettingsAndroid;
        captureStages?: Array<CaptureStage>;
        enableSwitchCameraButton?: Boolean;
        compressQuality?: number;
        resolution?: String;
        enableGoogleServices?: boolean;
        useRoot?: boolean;
        useEmulator?: boolean;
        useDeveloperMode?: boolean;
        useAdb?: boolean;
        useDebug?: boolean;
    });
}
