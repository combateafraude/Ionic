import { CaptureStage } from './capture-stage/capture-stage';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export declare class DocumentDetectorAndroidSettings {
    private customization;
    private sensorSettings;
    private captureStages;
    private enableSwitchCameraButton;
    constructor(customization: DocumentDetectorCustomizationAndroid, sensorSettings: SensorSettingsAndroid, captureStages: Array<CaptureStage>, enableSwitchCameraButton: Boolean);
}
