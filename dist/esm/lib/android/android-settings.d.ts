import { CaptureStage } from './capture-stage/capture-stage';
import { SensorSettingsAndroid } from './sensor-settings';
import { DocumentDetectorCustomizationAndroid } from './customization';
export declare class AndroidSettings {
    private customization;
    private sensorSettings;
    private captureStages;
    constructor(customization: DocumentDetectorCustomizationAndroid, sensorSettings: SensorSettingsAndroid, captureStages: Array<CaptureStage>);
}
